package com.example.http4s.ssl

import java.nio.file.Paths

import com.example.http4s.ExampleService
import org.http4s.server.SSLKeyStoreSupport.StoreInfo
import org.http4s.server.{ SSLKeyStoreSupport, Server, ServerApp, ServerBuilder }
import scalaz.concurrent.Task

trait SslExample extends ServerApp {
  // TODO: Reference server.jks from something other than one child down.
  val keypath = Paths.get("../server.jks").toAbsolutePath().toString()

  def builder: ServerBuilder with SSLKeyStoreSupport

  def server(args: List[String]): Task[Server] = builder
    .withSSL(StoreInfo(keypath, "password"), keyManagerPassword = "secure")
    .mountService(ExampleService.service, "/http4s")
    .bindHttp(8443)
    .start
}

