package ru.jenya.cli.interpret.command.grep

import java.io.{InputStream, OutputStream, OutputStreamWriter}
import java.nio.file.Paths
import java.util.regex.Pattern

import ru.jenya.cli.interpret.command.CMD
import ru.jenya.cli.interpret.envirnoment.Env
import ru.jenya.cli.interpret.utils.{Colors, Files}

import scala.collection.SortedMap
import scala.collection.immutable.TreeMap
import scala.io.Source


/** Executor of grep command */
object GrepCMD extends CMD {

  override def execute(s: String,
                       args: List[String],
                       in: InputStream,
                       out: OutputStream,
                       err: OutputStream,
                       ctx: Env): Boolean = {
    Files.file((), err) {
      val (xs, handlers) = GrepFlag.matchOptions(args)
      val (stream, regex) = if (xs.length == 1) {
        Source.fromInputStream(in).getLines().toStream -> xs.head
      } else if (xs.length >= 2) {
        var fileName = xs.tail.mkString
        if(!Paths.get(fileName).toFile.exists()){
          fileName = Paths.get(ctx.currentDir()).resolve(fileName).toString
        }
        Source.fromFile(fileName).getLines().toStream -> xs.head
      } else Stream.empty -> ""
      val (pattern, s) = handlers.foldLeft(Pattern.compile(regex) -> stream) {
        case ((pattern, stream), handler) => handler.beforeFound(pattern, stream)
      }
      val matched = s.map { s =>
        val matcher = pattern.matcher(s)
        var matches = TreeMap.empty[Int, Int]
        while (matcher.find()) {
          matches = matches.insert(matcher.start(), matcher.end())
        }
        MatchResult(s, matches, matches.nonEmpty)
      }
      val writer = new OutputStreamWriter(out)
      printGrepResult(handlers.foldLeft(matched) { case (s, h) => h.afterFound(s) }, writer)
      writer.close()
    }
    true
  }

  private def printGrepResult(stream: Stream[MatchResult], writer: OutputStreamWriter): Unit = {
    stream.filter(_.toPrint).foreach {
      case MatchResult(s, m, _) if m.isEmpty =>
        writer.write(s)
        writer.write(System.lineSeparator())
      case MatchResult(s, m, _) =>
        val ends = m.map(t => t._2 -> t._1)
        s.zipWithIndex.foreach {
          case (c, i) =>
            if (ends.contains(i)) {
              writer.write(Colors.RESET)
            }
            if (m.contains(i)) {
              writer.write(Colors.RED)
            }
            writer.write(c)
        }
        writer.write(System.lineSeparator())
    }
    writer.write(Colors.RESET)
    stream.force
  }

  final case class MatchResult(source: String, matches: SortedMap[Int, Int], toPrint: Boolean)

}
