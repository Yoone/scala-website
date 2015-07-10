package http

import util.URLHandler

import scala.io.StdIn

/**
 * Created by mecavity on 10/07/15.
 */
object PostData {
  def parseFile(request: Request, str: String): Unit = {
    val name: String = str.substring(str.indexOf("name=") + 6, str.indexOf("\"", str.indexOf("name=") + 6))
    val file = new PostFile()
    file.fileName = str.substring(str.indexOf("filename=") + 10, str.indexOf("\"", str.indexOf("filename=") + 10))
  }

  def parseField(request: Request, str: String): Unit = {
    if (str == "--" || str == "--\n")
      return
    // println("Field:" + str + "EndField")
    if (str.contains("filename")) {
      parseFile(request, str)
      return
    }
    val name: String = str.substring(str.indexOf("name=") + 6, str.indexOf("\"", str.indexOf("name=") + 6))
    val value: String = str.substring(str.indexOf("\n\n") + 2, str.indexOf("--"))
    request.POST += (URLHandler.decode(name) -> URLHandler.decode(value))
  }

  def multipart(request: Request, boundary: String): Unit = {
    var buf = StdIn.readLine()
    var data = ""
    var elt: String = null
    while (buf != null) {
      data += buf + "\n"
      buf = StdIn.readLine()
    }
    val arr: Array[String] = data.split(boundary)
    arr.foreach(parseField(request, _))
  }
}
