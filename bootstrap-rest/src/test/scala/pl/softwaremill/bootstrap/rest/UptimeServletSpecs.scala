package pl.softwaremill.bootstrap.rest

import pl.softwaremill.bootstrap.BootstrapServletSpec

class UptimeServletSpecs extends BootstrapServletSpec {

  def is = sequential                 ^
    "GET / on UptimServlet"           ^
    `should return status 200`        ^
    `should return JSON content type` ^
    `bust must contain value 10`
  end

  addServlet(new MockedUptimeServlet(), "/*")

  def `should return status 200` = get("/") {
    status should equalTo(200)
  }

  def `should return JSON content type` = get("/") {
    header.get("Content-Type").get should contain("application/json")
  }

  def `bust must contain value 10` = get("/") {
    body should contain("{\"value\":10}")
  }

}

class MockedUptimeServlet extends UptimeServlet {

  override def serverUptime() = {
    10
  }

}
