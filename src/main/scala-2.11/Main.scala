import annotation.Route
import controller.Controller

import reflect.runtime.universe._

/**
 * Created by yoone on 08/07/15.
 */

object Main {
  def main (args: Array[String]) {
    println("Content-Type: text/html\n")

    var uri: String = null
    try {
      uri = sys.env("REQUEST_URI")
    }
    catch {
      case e: NoSuchElementException => uri = "/upload" // Used for tests
    }

    val controller = typeOf[Controller.type].members
      .flatMap(c => c.annotations.find(_.tree.tpe == typeOf[Route]).map((c, _)))
      .filter(c => matchController(c._2.tree.children.tail.head.children.tail.head.toString(), uri))

    if (controller.isEmpty) {
      println("Controller not found")
    }
    else if (controller.size > 1) {
      println("Ambiguous controller matching")
    }
    else {
      val mirror = runtimeMirror(Controller.getClass.getClassLoader)
      val ctrl = mirror.reflect(Controller)
      val method = ctrl.reflectMethod(controller.head._1.asMethod)
      method()
    }
  }

  def matchController (src: String, ref: String): Boolean = {
    val s = src.substring(1, src.length - 1)
    s == ref // TODO: improve this
  }
}
