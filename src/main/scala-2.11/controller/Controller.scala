package controller

import controller.ascii.ImageToAscii

import model.{Game, Word}
import util.DatabaseHandler
import annotation.Route
import http.{Headers, Request}

import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by yoone on 08/07/15.
 */
object Controller {
  @Route("/")
  def index (request: Request): String = {
    request.vars += ("title" -> "Home")
    "index"
  }

  @Route("/ascii")
  def ascii (request: Request): String = {
    if (request.method == "POST") {
      request.vars += ("title" -> "ASCII result")
      val file = request.FILES.getOrElse("image", null)
      if (file == null || (file.contentType != "image/png" && file.contentType != "image/jpeg")) {
        request.vars += ("error" -> "Only PNG and JPEG files are accepted.")
      }
      else {
        request.vars += ("ascii" -> ImageToAscii.imgToAscii(file.tmpPath))
        return "ascii_result"
      }
    }

    request.vars += ("title" -> "Upload an image")
    "ascii_upload"
  }

  @Route("/hangman")
  def hangman (request: Request): String = {
    request.vars += ("title" -> "Play hangman!")
    "hangman"
  }

  @Route("/hangman/json")
  def hangman_json (request: Request): String = {
    Headers.sendContentType(Headers.ContentType.JSON)

    val words = TableQuery[Word]
    val games = TableQuery[Game]

    DatabaseHandler.database withSession {
      implicit session =>
        val game_id = (games returning games.map(_.id)) += (0, 1, "")
        println("{\"game_id\":" + game_id + "}")
    }

    ""
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
