package twitter


import slick.jdbc.MySQLProfile.api._
import twitter4j._

trait TwitterInstance {
  val twitterStream: TwitterStream = new TwitterStreamFactory(Util.config).getInstance
}

object Tweet extends App with TwitterInstance {
  twitterStream.addListener(Util.simpleStatusListener)
  twitterStream.filter(new FilterQuery().track(Array("modi", "smriti")).language(Array("en")))
  Thread.sleep(4000)
  twitterStream.cleanUp()
  twitterStream.shutdown()
}

class Twitter extends TwitterInstance {
  def testFun: Int = twitterStream match {
    case a: TwitterException => a.getStatusCode
    case _ => 200
  }
}

class TwitterDetails {

  val twitter = TableQuery(TwitterTable)

  def insert(username: String, userTweet: String) = {
    DatabaseConnection.db.run(twitter.map(t => (t.name, t.tweet)) += (username, userTweet))
  }

  //  val twitterSchema: Unit =Await.result(DatabaseConnection.db.run(DBIO.seq(
  //    twitter.schema.create
  //  )), Duration.Inf)

  case class TwitterTable(tag: Tag) extends Table[(String, String)](tag, "TWITTER_TABLE") {
    def * = (name, tweet)

    def name = column[String]("NAME")

    def tweet = column[String]("TWEET")
  }

}

object Util extends TwitterInstance {
  val config = new twitter4j.conf.ConfigurationBuilder()
    .setOAuthConsumerKey("..")
    .setOAuthConsumerSecret("..")
    .setOAuthAccessToken("..")
    .setOAuthAccessTokenSecret("..")
    .build


  def simpleStatusListener = new StatusListener() {
    def onStatus(status: Status) = {
      println(status.getText + "-----" + status.getUser + "===" + status.getId)
      //
      //      val ob=new TwitterDetails
      //      Await.result(ob.insert(status.getUser.getScreenName,status.getText), Duration.Inf)
    }

    def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}

    def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}

    def onException(ex: Exception) {
      ex.printStackTrace
    }

    def onScrubGeo(arg0: Long, arg1: Long) {}

    def onStallWarning(warning: StallWarning) {}
  }

}


