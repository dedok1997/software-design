package ru.jenya.cli.interpret.utils

import java.io.{FileNotFoundException, OutputStream}


object Files {
  import Streams._
  // help to handle file exceptions
  def file[T](defaut: T, out: OutputStream)(f: => T): T= {
    try{
      f
    }catch {
      case e: FileNotFoundException =>
        out.printError(e.getMessage)
        out.printNewLine()
        defaut
      case e =>
        throw e
    }
  }
}
