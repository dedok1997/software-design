package ru.jenya.cli.interpret.command

import java.io.{InputStream, OutputStream}

object EchoCMD extends CMD {
  override def execute(s: String,
                       args: List[String],
                       in: InputStream,
                       out: OutputStream,
                       err: OutputStream,
                       ctx: collection.mutable.Map[String, String]) = {
    val str = args.mkString(" ")
    out.write(str.getBytes)
    out.write(System.lineSeparator().getBytes)
    true
  }
}
