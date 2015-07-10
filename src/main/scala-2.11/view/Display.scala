package view

import http.Request

/**
 * Created by yoone on 10/07/15.
 */
object Display {
  def render (request: Request, view: String): Unit = {
    var contents = getView(view)

    // Search and replace "includes" (one level only)
    val includePattern = """\{%\s*include\s+([^\s]+)\s*%\}""".r
    includePattern.findAllIn(contents).matchData foreach {
      m => contents = contents.replace(m.matched, getView(m.group(1)))
    }

    print(contents)
  }

  private def getView (view: String): String = {
    val source = scala.io.Source.fromFile("views/" + view + ".html")
    var lines: String = null
    try {
      lines = source.mkString
    }
    catch {
      case e: Exception =>
        println("Could not load view")
        return ""
    }
    finally {
      source.close()
    }

    lines
  }
}
