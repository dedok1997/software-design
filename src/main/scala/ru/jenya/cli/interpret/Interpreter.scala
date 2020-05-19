package ru.jenya.cli.interpret

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, InputStream, OutputStream}

import ru.jenya.cli.interpret.command.CMD
import ru.jenya.cli.interpret.envirnoment.Env
import ru.jenya.cli.syntax.data.PipeLine

import scala.util.Try

/** Interpreter of cli command */
object Interpreter {

  import ru.jenya.cli.interpret.utils.StreamsImplicits._

  /** Execute command */
  def handle(cmd: PipeLine,
             in: InputStream,
             out: OutputStream,
             err: OutputStream,
             ctx: Env): Boolean = {
    val commands = cmd.commands
    var tempOut: ByteArrayOutputStream = null
    var tempErr = new ByteArrayOutputStream()
    var tempIn = in
    var result = true
    commands.foreach { cmd =>
      if (result) {
        val commandName = cmd.cmd
        val handler = CMD.resolve(commandName)
        tempOut = new ByteArrayOutputStream()
        result = Try {
          handler.execute(commandName, cmd.ops, tempIn, tempOut, tempErr, ctx)
        }.fold({ t =>
          tempErr.println(t.getMessage)
          false
        }, identity)
        tempIn = new ByteArrayInputStream(tempOut.toByteArray)
      }
    }
    if (tempErr.size() == 0) {
      out.write(tempOut.toByteArray)
    } else {
      err.write(tempErr.toByteArray)
    }
    result
  }
}
