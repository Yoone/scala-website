package http

import Parser.PostData

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
        formBoundary = pattern.findAllIn(sys.env("CONTENT_TYPE")).matchData.toList.head.group(1)
      }
      catch {
        case e @ (_: ArrayIndexOutOfBoundsException | _: NoSuchElementException) => formBoundary = null
      }

      if (formBoundary == null) {
        PostData.default(request)
      }
      else {
        PostData.multipart(request, formBoundary)
      }
    }
  }
}
