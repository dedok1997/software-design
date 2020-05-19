package ru.jenya.cli.interpret.command

import java.io.{File, InputStream, OutputStream}

import ru.jenya.cli.interpret.envirnoment.Env
import ru.jenya.cli.stream.StreamHelper

import scala.collection.JavaConverters._
import scala.util.Try

/** Runner of os shell comands */
object AnotherCMD extends CMD {
  def execute(cmd: String,
              args: List[String],
              in: InputStream,
              out: OutputStream,
              err: OutputStream,
              ctx: Env): Boolean = {
    Try {
      val commandLine = (cmd :: args).asJava
      val p = new ProcessBuilder().command(commandLine)
        .directory(new File(ctx.currentDir()))
        .start()
      val errors = p.getErrorStream
      val results = p.getInputStream
      StreamHelper.transfer(results, out)
      StreamHelper.transfer(errors, err)
    }.fold(t => err.write(t.getMessage.getBytes), identity)
    true
  }
}
