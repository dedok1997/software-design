package ru.jenya.cli.interpret.command

import java.io.{File, InputStream, OutputStream, OutputStreamWriter}
import java.nio.file.Paths

import ru.jenya.cli.interpret.envirnoment.Env
import ru.jenya.cli.interpret.utils.Files

import scala.io.Source

/** Runner of wc command */
object WCCMD extends CMD {
  def execute(s: String,
              args: List[String],
              in: InputStream,
              out: OutputStream,
              err: OutputStream,
              ctx: Env): Boolean = {
    Files.file((), err) {
      var fileName = args.mkString

      if(!Paths.get(fileName).toFile.exists()){
        fileName = Paths.get(ctx.currentDir()).resolve(fileName).toString
      }
      var lines = 0
      var words = 0
      val bytes = new File(fileName).length()
      val source = Source.fromFile(fileName)
      source.getLines().foreach { line =>
        lines = lines + 1
        words = words + line.split(" ").length
      }
      source.close()
      val writer = new OutputStreamWriter(out)
      writer.write(s"$bytes ")
      writer.write(s"$words ")
      writer.write(s"$lines ")
      writer.write(s"$fileName ${System.lineSeparator()}")
      writer.flush()
    }
    true
  }
}
