package ru.jenya.cli.interpret

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, InputStream, OutputStream}

import ru.jenya.cli.interpret.command.CMD
import ru.jenya.cli.syntax.data.PipeLine

// Interpreter of cli command
object Interpreter {
  //execute some command
  def handle(cmd: PipeLine, in: InputStream, out: OutputStream, ctx: collection.mutable.Map[String, String]): Boolean = {
    val commands = cmd.commands
    var tempOut: ByteArrayOutputStream = null
    var tempIn = in
    var result = true
    commands.foreach{cmd =>
      if(result) {
        val commandName = cmd.cmd
        val handler = CMD.resolve(commandName)
        tempOut = new ByteArrayOutputStream()
        result = handler.execute(commandName, cmd.ops, tempIn, tempOut, ctx)
        tempIn = new ByteArrayInputStream(tempOut.toByteArray)
      }
    }
    out.write(tempOut.toByteArray)
    result
  }
}
