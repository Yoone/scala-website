package view

import http.{Headers, Request}

/**
 * Created by yoone on 10/07/15.
 */
object Display {
  def render (request: Request, view: String): Unit = {
    Headers.sendContentType(Headers.ContentType.HTML)

    var contents = getView(view)

    // Search and replace "includes" (one level only)
    try {
      val includePattern = """\{%\s*include\s+([^\s]+)\s*%\}""".r
      includePattern.findAllIn(contents).matchData foreach {
        m => contents = contents.replace(m.matched, getView(m.group(1)))
      }
    }
    catch {
      case e: NoSuchElementException => // Ignore
    }

    // Search and replace vars
    try {
      val varPattern = """\{\{\s*([^\s]+)\s*\}\}""".r
      varPattern.findAllIn(contents).matchData foreach {
        m => contents = contents.replace(m.matched, request.vars.getOrElse(m.group(1), "").toString)
      }
    }
    catch {
      case e: NoSuchElementException => // Ignore
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
