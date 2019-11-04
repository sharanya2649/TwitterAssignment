package com.techsophy.twitter.db

import com.techsophy.twitter.db.connection.{DatabaseConnection, MySQLDBConnector}

import scala.concurrent.Future

trait TwitterQuery extends TwitterDetails {

  import driver.api._

  def insert(username: String, userTweet: String) = {

    db.run(twitter.map(t => (t.name, t.tweet)) += (username, userTweet))
  }

  def read =
    db.run(twitter.result.head)

  def getAllTweets(): Future[List[String]] = {
    db.run(twitter.map { x => x.tweet }.to[List].result)
  }

}

trait TwitterDetails extends DatabaseConnection {

  import driver.api._

  val twitter = TableQuery[TwitterTable]


  class TwitterTable(tag: Tag) extends Table[TweetData](tag, "TWITTER_TABLE") {
    def * = (name, tweet) <> (TweetData.tupled, TweetData.unapply)

    def name = column[String]("name")

    def tweet = column[String]("tweet")
  }

  //  Await.result(db.run(DBIO.seq(
  //      twitter.schema.create
  //    )), Duration.Inf)

}

case class TweetData(name: String, tweet: String)

object TwitterQueryObj extends TwitterQuery with MySQLDBConnector
