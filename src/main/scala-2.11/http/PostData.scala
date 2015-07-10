package Parser
import util.URLHandler

import scala.io.StdIn
import http.Request

/**
 * Created by mecavity on 10/07/15.
 */
object PostData {
  def getStdIn(): String = {
    var buf = StdIn.readLine()
    var data = ""
    while (buf != null) {
      data += buf + "\n"
      buf = StdIn.readLine()
    }
    // println("data:"  + data)
    data
  }

  def default(request: Request): Unit =  {
    var data = getStdIn()
    var i = 0
    var name = ""
    var value = ""
    var getName = true

    while(i < data.length) {
      if (data(i) == '&') {
        getName = !getName
        request.POST += (URLHandler.decode(name) -> URLHandler.decode(value))
        name = ""
        value = ""
      }
      else if (i + 1 == data.length) {
        value += data(i)
        request.POST += (name -> value)
      }
      else if (data(i) == '=')
        getName = !getName
      else if (getName)
        name += data(i)
      else
        value += data(i)
      i += 1
    }
  }

  def multipart(data: String, boundary: String): Map[String, String] =  {
    var map: Map[String, String] = Map()
    var str = data
    var i = 0
    var name = ""
    var getName = false
    var line = ""
    while (str != null) {
      while (str(i) != '\r' && str(i + 1) != '\n') {
        line += data(i)
        i += 1
      }
      str = str.substring(i + 2, str.length)
      print(line)
      line = ""
      i = 0
    }

    map
  }
}
