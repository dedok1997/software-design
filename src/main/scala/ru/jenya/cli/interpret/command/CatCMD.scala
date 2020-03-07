package ru.jenya.cli.interpret.command

import java.io.{BufferedReader, FileInputStream, FileReader, InputStream, OutputStream}

object CatCMD extends CMD {
  override def execute(s: String,
                       args: List[String],
                       in: InputStream,
                       out: OutputStream,
                       ctx: collection.mutable.Map[String, String]): Boolean = {
    val fileName = args.mkString
    val file = new BufferedReader(new FileReader(fileName))
    Iterator.continually(file.read()).takeWhile(_ != -1).foreach(out.write)
    file.close()
    true
  }
}