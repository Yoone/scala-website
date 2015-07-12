package model

import scala.slick.driver.MySQLDriver.simple._

/**
 * Created by yoone on 12/07/15.
 */
class Word (tag: Tag) extends Table[(Int, String)](tag, "word") {
  def id = column[Int]("id", O.PrimaryKey)
  def word = column[String]("word")
  def * = (id, word)
}
