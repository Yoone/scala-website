package model

import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by yoone on 12/07/15.
 */
class Game (tag: Tag) extends Table[(Int, Int, String)](tag, "game") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def word_id = column[Int]("word_id")
  def guessed = column[String]("guessed")
  def * = (id, word_id, guessed)
  def word = foreignKey("word_fk", word_id, TableQuery[Word])(_.id)
}
