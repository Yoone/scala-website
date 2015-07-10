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
}
