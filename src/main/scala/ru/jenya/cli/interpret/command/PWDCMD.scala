package ru.jenya.cli.interpret.command

import java.io.{InputStream, OutputStream}

object PWDCMD extends CMD {

  val currentDir = "cur_dir"

  def execute(s: String,
              args: List[String],
              in: InputStream,
              out: OutputStream,
              ctx: collection.mutable.Map[String, String]) = {
    val current = ctx.getOrElse(currentDir, "./")
    out.write(current.getBytes)
    true
  }
}
