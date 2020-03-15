package ru.jenya.cli.interpret.command

import java.io.{File, InputStream, OutputStream, OutputStreamWriter}

import ru.jenya.cli.interpret.utils.Files

import scala.io.Source

object WCCMD extends CMD {
  def execute(s: String,
              args: List[String],
              in: InputStream,
              out: OutputStream,
              err: OutputStream,
              ctx: collection.mutable.Map[String, String]) = {
    Files.file((), err) {
      val fileName = args.mkString
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
      writer.write(s"${bytes.toString} ")
      writer.write(s"${words.toString} ")
      writer.write(s"${lines.toString} ")
      writer.write(s"${fileName} ${System.lineSeparator()}")
      writer.flush()
    }
    true
  }
}
