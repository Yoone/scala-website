package http

import util.URLHandler

import scala.io.StdIn

/**
 * Created by mecavity on 10/07/15.
 */
object PostData {
  def parseFile(request: Request, dataSource: String, boundary: String): Unit = {
    var buf: String = ""
    var data = dataSource
    while (buf != null && !data.contains("\n\n")) {
      data += buf + "\n"
      buf = StdIn.readLine()
    }
    val name: String = data.substring(data.indexOf("name=") + 6, data.indexOf("\"", data.indexOf("name=") + 6))
    var file = new PostFile()
    file.fileName = data.substring(data.indexOf("filename=") + 10, data.indexOf("\"", data.indexOf("filename=") + 10))
    file.tmpPath = "files/" + file.fileName // TODO generate tmp path

    // Read Binary File
    URLHandler.getFile(request, boundary, StdIn)
  }

  def parseField(request: Request, str: String): Unit = {
    if (str == "--" || str == "--\n")
      return
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
      // Check if a byte read will have to be performed
      if (data.contains("filename")) {
        parseFile(request, data, boundary) // TODO
        data = ""
        return // TODO REMOVE
      }
      else if (data.contains(boundary)) {
        data = data.substring(0, data.indexOf(boundary))
        parseField(request, data)
        data = ""
      }
    }
  }
}
