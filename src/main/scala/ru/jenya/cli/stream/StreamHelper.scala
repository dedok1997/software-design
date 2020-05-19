package ru.jenya.cli.stream

import java.io.{InputStream, OutputStream}


/** Helper for streams */
object StreamHelper {

  def transfer(in: InputStream, out: OutputStream): Unit =
    Stream.continually(in.read).takeWhile(_ != -1).foreach(out.write)
}
