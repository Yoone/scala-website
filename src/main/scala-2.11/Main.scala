import annotation.Route
import controller.Controller
import http.{Headers, Request}
import view.Display

import reflect.runtime.universe._

/**
 * Created by yoone on 08/07/15.
 */

object Main {
  def main (args: Array[String]) {
    println("Content-Type: text/html\n")

    var uri: String = null
    var queryString: String = null
    try {
      val rqUri = sys.env("REQUEST_URI").split('?')
      uri = rqUri(0)
      if (rqUri.length > 1)
        queryString = rqUri(1)
    }
    catch {
      case e: NoSuchElementException => // Do nothing, wrong environment
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

      // Handle HTTP request
      val request = new Request
      Headers.parse(request, queryString)

      // Run controller
      val view = method(request).toString

      // Render view
      Display.render(request, view)
    }
  }

  def matchController (src: String, ref: String): Boolean = {
    val s = src.substring(1, src.length - 1)
    s == ref // TODO: improve this
  }
}
