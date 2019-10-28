package twitter


import twitter4j._
import slick.jdbc.MySQLProfile.api._
import slick.lifted.QueryBase
import slick.sql.FixedSqlAction

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}


object Tweet {
  def main(args: Array[String]) {
    val twitterStream = new TwitterStreamFactory(Util.config).getInstance
//    val p: Int =twitterStream.getConfiguration.getHttpProxyPort
    twitterStream.addListener(Util.simpleStatusListener)
    twitterStream.filter(new FilterQuery().track(Array("modi","smriti")).language(Array("en")))
    Thread.sleep(4000)
    twitterStream.cleanUp
    twitterStream.shutdown
  }
}
class Twitter{
  def testFun:Int={
    val twitterStream2 = new TwitterStreamFactory(Util.config).getInstance
    val p: Int =twitterStream2.getConfiguration.getHttpProxyPort
    p
  }
}
class TwitterDetails{
  case class TwitterTable(tag:Tag) extends Table[(String,String)](tag,"TWITTER_TABLE") {
    def name= column[String]("NAME")
    def tweet=column[String]("TWEET")
    def * =(name,tweet)
  }
  val twitter= TableQuery(TwitterTable)

//  val twitterSchema: Unit =Await.result(DatabaseConnection.db.run(DBIO.seq(
//    twitter.schema.create
//  )), Duration.Inf)


  def insert(username:String,userTweet:String) = {
    twitter.map(t=>(t.name,t.tweet)) += (username,userTweet)
  }
}
object Util {
  val config = new twitter4j.conf.ConfigurationBuilder()
    .setOAuthConsumerKey(".." )
    .setOAuthConsumerSecret("..")
    .setOAuthAccessToken("..")
    .setOAuthAccessTokenSecret("..")
    .build

  def simpleStatusListener = new StatusListener() {
    def onStatus(status: Status) ={
      //      println(DataObjectFactory.getRawJSON(status))
      println(status.getText +"-----"+ status.getUser+"==="+status.getId)

      val ob=new TwitterDetails
      Await.result(DatabaseConnection.db.run(ob.insert(status.getUser.getScreenName,status.getText)), Duration.Inf)
    }
    def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}
    def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}
    def onException(ex: Exception) { ex.printStackTrace }
    def onScrubGeo(arg0: Long, arg1: Long) {}
    def onStallWarning(warning: StallWarning) {}
  }

}


