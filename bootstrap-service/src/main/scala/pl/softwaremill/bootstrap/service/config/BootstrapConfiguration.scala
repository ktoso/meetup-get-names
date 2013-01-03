package pl.softwaremill.bootstrap.service.config

import pl.softwaremill.common.conf.{ MapWrapper, Configuration, Config }
import java.util

object BootstrapConfiguration {

  val config: Config[String, String] = try {
    Configuration.get("application")
  } catch {
    case e: RuntimeException => new MapWrapper(new util.HashMap[String, String]())
  }

}
