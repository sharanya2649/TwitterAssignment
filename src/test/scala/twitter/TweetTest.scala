package twitter

import org.scalatest.FunSuite

import scala.concurrent.Await

class TweetTest extends FunSuite{
  test("configuration testing") {
    val tweet = new Twitter
    val input = tweet.testFun
    val output = 200
    assert(input === output)
  }
}
