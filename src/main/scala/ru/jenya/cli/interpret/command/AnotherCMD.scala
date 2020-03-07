package ru.jenya.cli.interpret.command

import java.io.{File, InputStream, OutputStream}

import scala.collection.JavaConverters._
import ru.jenya.cli.interpret.envirnoment.EnvUtils._

object AnotherCMD extends CMD {
  def execute(cmd: String,
              args: List[String],
              in: InputStream,
              out: OutputStream,
              ctx: collection.mutable.Map[String, String]): Unit = {
    val commandLine = (cmd :: args).asJava
    val p = new ProcessBuilder().command(commandLine)
      .directory(new File(currentDir(ctx)))
      .start()
    //TODO make output stream
    val results = p.getInputStream
    Stream.continually(results.read()).takeWhile(_ != -1).foreach(out.write)

  }
}
