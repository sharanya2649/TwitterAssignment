package com.techsophy.twitter.db.connection
import slick.jdbc.JdbcProfile

trait DatabaseConnection {

  val driver: JdbcProfile

  def db: driver.backend.DatabaseDef

}