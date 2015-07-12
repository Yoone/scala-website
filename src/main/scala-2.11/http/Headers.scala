package http

/**
 * Created by yoone on 10/07/15.
 */
object Headers {
  def parse (request: Request, queryString: String): Unit = {
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
        QueryString.parsePOST(request)
      }
      else {
        PostData.multipart(request, formBoundary)
      }
    }

    QueryString.parseGET(request, queryString)
  }

  object ContentType extends Enumeration {
    type ContentType = Value
    val HTML, JSON = Value // Add more if needed
  }

  import ContentType._
  def sendContentType (contentType: ContentType, charset: String = "UTF-8"): Unit = {
    print("Content-Type: ")
    contentType match {
      case JSON =>
        print("application/json")
      case _ =>
        print("text/html")
    }
    print("; charset=" + charset + "\n\n")
  }
}
