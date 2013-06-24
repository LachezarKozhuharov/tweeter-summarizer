import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "twitter-summarizer"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    "org.apache.httpcomponents" % "httpclient" % "4.2.5",
	"com.google.oauth-client" % "google-oauth-client" % "1.15.0-rc",
	"org.mongodb" % "mongo-java-driver" % "2.11.1"
  )
  
  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
