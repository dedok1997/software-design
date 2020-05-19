package ru.jenya.cli.interpret.command

import java.io.{InputStream, OutputStream}

import ru.jenya.cli.interpret.envirnoment.Env

/** Runner of echo command */
object EchoCMD extends CMD {
  override def execute(s: String,
                       args: List[String],
                       in: InputStream,
                       out: OutputStream,
                       err: OutputStream,
                       ctx: Env): Boolean = {
    val str = args.mkString(" ")
    out.write(str.getBytes)
    out.write(System.lineSeparator().getBytes)
    true
  }
}
