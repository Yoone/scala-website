package http

import util.URLHandler

import scala.io.StdIn

/**
 * Created by yoone on 10/07/15.
 */
object QueryString {
  def parseGET (request: Request, query: String): Unit = {
    if (query != null) {
      query.split('&').foreach {
        v =>
          val a = v.split('=')
          if (a.length > 0) {
            request.GET += (URLHandler.decode(a(0)) -> (if (a.length > 1) URLHandler.decode(a(1)) else ""))
          }
      }
    }
  }

  def parsePOST (request: Request): Unit = {
    var buf = StdIn.readLine()
    var data = ""
    while (buf != null) {
      data += buf + "\n"
      buf = StdIn.readLine()
    }

    data.dropRight(1).split('&').foreach {
      v =>
        val a = v.split('=')
        if (a.length > 0) {
          request.POST += (URLHandler.decode(a(0)) -> (if (a.length > 1) URLHandler.decode(a(1)) else ""))
        }
    }
  }
}
