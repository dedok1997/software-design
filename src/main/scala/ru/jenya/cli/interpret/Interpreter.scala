package ru.jenya.cli.interpret

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, InputStream, OutputStream}

import ru.jenya.cli.interpret.command.CMD
import ru.jenya.cli.syntax.data.PipeLine

object Interpreter {
  def handle(cmd: PipeLine, in: InputStream, out: OutputStream, ctx: collection.mutable.Map[String, String]) {
    val commands = cmd.commands
    var tempOut: ByteArrayOutputStream = null
    var tempIn = in
    commands.foreach { cmd =>
      val commandName = cmd.cmd
      val handler = CMD.resolve(commandName)
      tempOut = new ByteArrayOutputStream()
      handler.execute(commandName, cmd.ops, tempIn, tempOut, ctx)
      tempIn = new ByteArrayInputStream(tempOut.toByteArray)
    }
    out.write(tempOut.toByteArray)
  }
}
