package ru.jenya.cli.interpret.command

import java.io.{InputStream, OutputStream}

import ru.jenya.cli.interpret.envirnoment.Env

/** Runner of pwd command */
object PWDCMD extends CMD {


  def execute(s: String,
              args: List[String],
              in: InputStream,
              out: OutputStream,
              err: OutputStream,
              ctx: Env): Boolean = {
    val current = ctx.currentDir()
    out.write(current.getBytes)
    out.write(System.lineSeparator().getBytes)
    true
  }
}
