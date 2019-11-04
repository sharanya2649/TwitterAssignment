package com.techsophy.twitter.db

import java.util.UUID

import com.techsophy.twitter.db.connection.DatabaseConnection
import com.typesafe.config.ConfigFactory
import slick.jdbc.H2Profile

trait TestDBConnector extends DatabaseConnection {

  val driver = H2Profile

  import driver.api._

  override def db = {
    val randomDB = "jdbc:h2:mem:test" + UUID.randomUUID().toString() + ";"
    val config = ConfigFactory.load("test-dbconfiguration.conf")
    val dbDriver = config.getString("db.driver")
    val url = randomDB + config.getString("db.url")
    Database.forURL(url, driver = dbDriver)
  }
}