package controller

import annotation.Route
import http.Request

/**
 * Created by yoone on 08/07/15.
 */
object Controller {
  @Route("/")
  def index (request: Request): String = {
    request.vars += ("title" -> "Home")
    "index"
  }

  @Route("/ascii/upload")
  def upload (request: Request): String = {
    request.vars += ("title" -> "Upload an image")
    "ascii_upload"
  }

  @Route("/ascii/result")
  def result (request: Request): String = {
    request.vars += ("title" -> "ASCII result")
    "ascii_result"
  }

  @Route("/test/form")
  def test_form (request: Request): String = {
    request.vars += ("title" -> "Testing a simple form")
    if (request.POST.nonEmpty) {
      request.vars += ("name" -> request.POST.getOrElse("name", "Anonymous"))
      "test_form_result"
    }
    else {
      "test_form"
    }
  }
}
