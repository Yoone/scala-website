package http

import Parser.PostData

import scala.io.StdIn

/**
 * Created by yoone on 10/07/15.
 */
object Headers {
  def parse (request: Request): Unit = {
    request.method = sys.env("REQUEST_METHOD")

    if (request.method == "POST") {
      var formBoundary: String = null
      try {
        val pattern = """boundary=(.+)$""".r
        formBoundary = pattern.findAllIn(sys.env("CONTENT_TYPE")).matchData.toList.head.group(2)
      }
      catch {
        case e @ (_: ArrayIndexOutOfBoundsException | _: NoSuchElementException) => formBoundary = null
      }

      // << BEGIN reading from StdIn
      // TODO: remove until END
      // TODO: remove import (StdIn)
      /*var buf = StdIn.readLine()
      var data = ""
      while (buf != null) {
        data += buf + "\n"
        buf = StdIn.readLine()
      }*/
      // END >>

      if (formBoundary == null) {
        PostData.default(request)
      }
      else {
        // TODO: parse multipart data
        // PostData.multipart(data, request.POST, formBoundary, request.FILES)
      }
    }
  }
}
