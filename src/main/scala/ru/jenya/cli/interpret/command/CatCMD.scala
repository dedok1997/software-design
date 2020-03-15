package ru.jenya.cli.interpret.command

import java.io.{BufferedReader, FileReader, InputStream, OutputStream}

import ru.jenya.cli.interpret.utils.Files

object CatCMD extends CMD {
  override def execute(s: String,
                       args: List[String],
                       in: InputStream,
                       out: OutputStream,
                       err: OutputStream,
                       ctx: collection.mutable.Map[String, String]): Boolean = {
    Files.file((), err) {
      val fileName = args.mkString
      val file = new BufferedReader(new FileReader(fileName))
      Iterator.continually(file.read()).takeWhile(_ != -1).foreach(out.write)
      file.close()
    }
    true
  }
}
