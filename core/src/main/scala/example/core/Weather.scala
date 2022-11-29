package example.core

import gigahorse._, support.okhttp.Gigahorse
import scala.concurrent._, duration._
import play.api.libs.json._

object Weather {
  lazy val http = Gigahorse.http(Gigahorse.config)

  def weather: Future[String] = {
    val baseUrl = "https://api.weather.gov/alerts/active"
    val rLoc = Gigahorse.url(baseUrl).get.
      addQueryString("area" -> "NY")
    import ExecutionContext.Implicits.global
    for {
      loc <- http.run(rLoc, parse)
      weather <- http.run(rLoc, parse)
    } yield weather.toString
  }

  private def parse = Gigahorse.asString andThen Json.parse
}