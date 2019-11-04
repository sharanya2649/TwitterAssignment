package com.techsophy.twitter.util

import com.techsophy.twitter.db.{TwitterDetails, TwitterQuery, TwitterQueryObj}
import twitter4j._

import scala.collection.mutable.ListBuffer

trait Tweet extends StatusListener {

  val twitterQuery: TwitterQuery

  val config = new twitter4j.conf.ConfigurationBuilder()
    .setOAuthConsumerKey("cDPSKmyJDYofdSjktgtfMqGl9" )
    .setOAuthConsumerSecret("Epf1waJw3536qy4dQzb6M3QQFirNrpXEo6OpHEijiSFoyNMlIr")
    .setOAuthAccessToken("3221559674-1VVA4Hp7RvLgn0v5L7OP5Ff9FFruY9fB2PXDMXI")
    .setOAuthAccessTokenSecret("cZikVe3LjR60bV9nyTmRXOhhHk6oHPRkWXBTVCjvMYyWv")
    .build

    def onStatus(status: Status): Unit = {
      println(status.getText)
      twitterQuery.insert(status.getUser.getName, status.getText)
    }

    def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) {}
    def onTrackLimitationNotice(numberOfLimitedStatuses: Int) {}
    def onException(ex: Exception) { ex.printStackTrace }
    def onScrubGeo(arg0: Long, arg1: Long) {}
    def onStallWarning(warning: StallWarning) {}

}

object Tweet extends Tweet {
  override val twitterQuery: TwitterQuery = TwitterQueryObj
}
