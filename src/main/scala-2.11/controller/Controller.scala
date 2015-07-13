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

    var all_guesses = ""
    var guess = " "

    // val current_game: Int = request.GET.getOrElse("id", )
    val id = request.GET.getOrElse("id", 0)
    val current_game: Int = id match {
      case x: String => x.toInt
      case _ => 0
    }

    request.GET.getOrElse("action", "") match {
      case "new" => DatabaseHandler.database withSession {
        implicit session =>
          val game_id = (games returning games.map(_.id)) +=(0, 1, "")
          println("{\"game_id\":" + game_id + "}")
          return ""
      }
      case x if current_game == 0 => println("{\"error\":\"Bad game id\"}")
        return ""
        // TODO check if current_game exists
      case x => DatabaseHandler.database withSession {
        implicit session =>
          all_guesses = games.filter(_.id === current_game).map(_.guessed).first.toLowerCase
          all_guesses += (if (all_guesses.contains(x)) "" else x).toLowerCase
          games.filter(_.id === current_game).map(_.guessed).update(all_guesses)
          guess = x.toLowerCase
      }
    }
    var word = ""
    DatabaseHandler.database withSession {
      implicit session =>
        val game_word_id = games.filter(_.id === current_game).map(_.word_id)
        word = words.filter(_.id in game_word_id).map(_.word).first.toLowerCase
    }

    var result = ""
    word.foreach(x => result += ((if (all_guesses.contains(x)) x else '_') + " "))
    result = result.trim.toUpperCase

    var finished = true
    word.foreach(x => if (!all_guesses.contains(x)) finished = false)

    // success, finished, word
    println("{\"word\":" + result + "\",\"success\":" + word.contains(guess) + ",\"finished\":" + finished + "}")
    //println("Game: " + current_game)
    //println("Objective: " + word)
    //println("Guessed: " + all_guesses)
    //println("Current: " + result)

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
