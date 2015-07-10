package controller

import annotation.Route
import http.Request

/**
 * Created by yoone on 08/07/15.
 */
object Controller {
  @Route("/")
  def index (request: Request): String = {
    "index"
  }

  @Route("/ascii/upload")
  def upload (request: Request): String = {
    "upload"
  }

  @Route("/ascii/result")
  def result (request: Request): String = {
    "result"
  }
}
