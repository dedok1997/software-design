package ru.jenya.cli.interpret.command

import java.io.{BufferedReader, FileReader, InputStream, OutputStream}
import java.nio.file.Paths

import ru.jenya.cli.interpret.envirnoment.Env
import ru.jenya.cli.interpret.utils.Files
import ru.jenya.cli.stream.StreamHelper

/** Cat command executor */
object CatCMD extends CMD {
  override def execute(s: String,
                       args: List[String],
                       in: InputStream,
                       out: OutputStream,
                       err: OutputStream,
                       ctx: Env): Boolean = {
    if(args.nonEmpty) {
      Files.file((), err) {
        var fileName = args.mkString
        if(!Paths.get(fileName).toFile.exists()){
          fileName = Paths.get(ctx.currentDir()).resolve(fileName).toString
        }
        val file = new BufferedReader(new FileReader(fileName))
        Iterator.continually(file.read()).takeWhile(_ != -1).foreach(out.write)
        file.close()
      }
    }else{
      Iterator.continually(in.read()).takeWhile(_ != -1).foreach(out.write)
    }
    true
  }
}
