package http

import util.URLHandler

import java.io._

/**
 * Created by mecavity on 10/07/15.
 */
object PostData {
  val reader = new InputStreamReader(System.in)

  def saveBinaryFile(request: Request, data: String): PostFile = {
    val file = new PostFile()
    file.fileName = data.substring(data.indexOf("filename=") + 10, data.indexOf("\"", data.indexOf("filename=") + 10))
    file.tmpPath = "files/" + file.fileName // TODO generate tmp path

    val output = new DataOutputStream(new FileOutputStream(file.tmpPath))
    var b: Int = System.in.read()
    try {
      while (b != -1) {
        output.write(b)
        b = System.in.read()
      }
    }
    finally output.close()

    file
  }

  def parseFile(request: Request, dataSource: String): Unit = {
    var b: Int = System.in.read()
    var data = dataSource
    while (b != -1 && !data.contains("\r\n\r")) { // TODO check why we need to leave last '\n'
      data += b.toChar
      b = System.in.read()
    }
    val name: String = data.substring(data.indexOf("name=") + 6, data.indexOf("\"", data.indexOf("name=") + 6))

    // Save Binary File
    request.FILES += (name -> saveBinaryFile(request, data))
  }

  def parseField(request: Request, str: String): Unit = {
    if (str == "--" || str == "--\n")
      return
    val name: String = str.substring(str.indexOf("name=") + 6, str.indexOf("\"", str.indexOf("name=") + 6))
    val value: String = str.substring(str.indexOf("\r\n\r\n") + 4, str.indexOf("--"))
    request.POST += (URLHandler.decode(name) -> URLHandler.decode(value))
  }

  def multipart (request: Request, boundary: String): Unit = {
    var data = ""
    var b: Int = System.in.read()
    while (b != -1) {
      data += b.toChar
      if (data.contains(boundary)) {
        data = data.substring(0, data.indexOf(boundary))
        parseField(request, data)
        data = ""
      }
      else if (data.contains("filename")) {
        parseFile(request, data) // TODO this ends parsing, need to check end of binary file
        data = ""
      }
      b = System.in.read()
    }
  }
}
