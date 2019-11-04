package com.techsophy.twitter.app

import com.techsophy.twitter.util.Tweet
import twitter4j.{FilterQuery, TwitterStreamFactory}

object Launch extends App {

  val twitterStream = new TwitterStreamFactory(Tweet.config).getInstance

  twitterStream.addListener(Tweet)
  twitterStream.filter(new FilterQuery().track(Array("modi", "smriti")).language(Array("en")))
  Thread.sleep(4000)
  twitterStream.cleanUp()
  twitterStream.shutdown()
}
