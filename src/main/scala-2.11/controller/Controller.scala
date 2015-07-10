package controller

import annotation.Route
import http.Request

/**
 * Created by yoone on 08/07/15.
 */
object Controller {
  @Route("/")
  def index (request: Request): Unit = {
    println("Index page")
  }

  @Route("/ascii/upload")
  def upload (request: Request): Unit = {
    println("Upload page")

    val source = scala.io.Source.fromFile("views/upload.html") // TODO: util to load views
    val lines = try source.mkString finally source.close()

    println(lines)
  }

  @Route("/ascii/result")
  def result (request: Request): Unit = {
    /*var buf = StdIn.readLine()
    var data = ""
    while (buf != null) {
      data += buf + "\n"
      buf = StdIn.readLine()
    }

    println(data + "\n\n")

    sys.env.foreach(println(_))*/
  }
}
