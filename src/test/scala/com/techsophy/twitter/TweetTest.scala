package com.techsophy.twitter

import com.techsophy.twitter.util.Tweet
import org.scalatest.FunSuite

import scala.concurrent.Await
import scala.concurrent.duration._

class TweetTest extends FunSuite {
  test("testing") {
    val input = Await.result(Tweet.twitterQuery.insert("abc", "pqrs"), 10 seconds)
    assert(input === 1)
  }

  //  val statusList = ListBuffer[Status]()
  //
  //  val tweet: Tweet = new Tweet {
  //    val twitterQuery = mock[TwitterQuery]
  //    override def onStatus(status: Status){
  //     statusList.append(status)
  //    }
  //  }
  //  test("test") {
  //  }

}