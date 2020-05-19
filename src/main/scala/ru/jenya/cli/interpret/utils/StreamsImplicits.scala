package ru.jenya.cli.interpret.utils

import java.io.OutputStream

/** Stream implicits
 *  {{{
 *  import StreamsImplicits._
 *  val out: OutputStream
 *  out.println("")
 *  }}}
 */
object StreamsImplicits {

  implicit class OutputStreamHelper(val out: OutputStream) extends AnyVal {

    def print(s: String): Unit = out.write(s.getBytes)

    def println(s: String): Unit = {
      out.write(s.getBytes)
      printNewLine()
    }

    def printNewLine(): Unit = out.write(System.lineSeparator().getBytes)

  }

}
