package ru.jenya.cli.interpret.utils

import java.io.OutputStream

object Streams {

  implicit class OutputStreamHelper(val out: OutputStream) extends AnyVal {

    def print(s: String): Unit = out.write(s.getBytes)

    def println(s: String): Unit = {
      out.write(s.getBytes)
      printNewLine()
    }

    def printNewLine(): Unit = out.write(System.lineSeparator().getBytes)

  }

}
