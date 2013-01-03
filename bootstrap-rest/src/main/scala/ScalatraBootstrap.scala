import com.mongodb.casbah.{ MongoDB, MongoConnection }
import java.util.concurrent.{ ScheduledExecutorService, Executors }
import pl.softwaremill.bootstrap.rest.SlurpServlet
import org.scalatra._
import javax.servlet.ServletContext
import pl.softwaremill.bootstrap.service.meetup.MeetupNamesSlurper

/**
 * This is the ScalatraBootstrap bootstrap file. You can use it to mount servlets or
 * filters. It's also a good place to put initialization code which needs to
 * run at application start (e.g. database configurations), and init params.
 */
class ScalatraBootstrap extends LifeCycle {

  val Prefix = "/rest"
  val MongoKey = "MONGO_DB"

  override def init(context: ServletContext) {

    val slurperService = new MeetupNamesSlurper

    // Mount one or more servlets
    context.mount(new SlurpServlet(slurperService), Prefix + "/slurp")
  }

  override def destroy(context: ServletContext) {
    context.get(MongoKey).foreach(_.asInstanceOf[MongoDB].underlying.getMongo.close())
  }

}
