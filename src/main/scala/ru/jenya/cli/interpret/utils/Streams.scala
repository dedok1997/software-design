package ru.jenya.cli.interpret.utils

import java.io.OutputStream

object Streams {
  implicit class OutputStreamHelper(val out: OutputStream) extends AnyVal {

    def print(s: String): Unit = out.write(s.getBytes)
    def printError(s: String): Unit = {
      out.write(Colors.RED.getBytes)
      print(s)
      out.write(Colors.RESET.getBytes)
    }
    def printNewLine(): Unit = out.write(System.lineSeparator().getBytes)

  }
}
