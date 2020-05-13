package ru.jenya.cli.interpret.utils

import java.io.{FileNotFoundException, OutputStream}


object Files {

  import StreamsImplicits._

  /** Help to handle file exceptions */
  def file[T](defaut: T, out: OutputStream)(f: => T): T = {
    try {
      f
    } catch {
      case e: FileNotFoundException =>
        out.println(e.getMessage)
        defaut
      case e =>
        throw e
    }
  }
}
