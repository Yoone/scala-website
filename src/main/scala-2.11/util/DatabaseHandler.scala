package util

import slick.jdbc.JdbcBackend.Database

/**
 * Created by yoone on 12/07/15.
 */
object DatabaseHandler {
  val database = Database.forConfig("db")
}
