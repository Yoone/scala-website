package controller

import slick.jdbc.JdbcBackend.Database

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
  def ascii_upload (request: Request): String = {
    request.vars += ("title" -> "Upload an image")
    "ascii_upload"
  }

  @Route("/ascii/result")
  def ascii_result (request: Request): String = {
    request.vars += ("title" -> "ASCII result")
    "ascii_result"
  }

  @Route("/db/hangman")
  def db_hangman (request: Request): String = {
    request.vars += ("title" -> "Play hangman!")

    // TODO: remove the following code
    // Used only for testing purposes
    val db = Database.forConfig("db")
    db.createConnection()

    "db_hangman"
  }

  @Route("/test/form")
  def test_form (request: Request): String = {
    request.vars += ("title" -> "Testing a simple form")
    if (request.method == "POST") {
      request.vars += ("name" -> request.POST.getOrElse("name", "Anonymous"))
      "test_form_result"
    }
    else {
      "test_form"
    }
  }
}
