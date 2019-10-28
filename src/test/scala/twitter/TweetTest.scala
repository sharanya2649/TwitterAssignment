package twitter

import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{BeforeAndAfter, FunSuite}

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}


class TweetTest extends FunSuite with BeforeAndAfter with MockitoSugar {
  val tweet = new Twitter
  test("mock testing") {
    val m = mock[TwitterDetails]
    when(m.insert("abc", "pqrs")).thenReturn(Future.successful(1))
    val input = Await.result(m.insert("abc", "pqrs"), 10 seconds)
    assert(input === 1)
  }
  test("test2") {
    val input = tweet.testFun
    val output = 200
    assert(input === output)
  }
}
