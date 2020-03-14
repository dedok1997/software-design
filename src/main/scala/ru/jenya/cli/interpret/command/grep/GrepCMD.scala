package ru.jenya.cli.interpret.command.grep

import scala.io.{Source, StdIn}
import java.io.{InputStream, OutputStream, OutputStreamWriter}
import java.util.regex.{Matcher, Pattern}

import ru.jenya.cli.interpret.command.CMD

import scala.collection.immutable.TreeMap
import scala.collection.{SortedMap, mutable}

object GrepCMD extends CMD {

  override def execute(s: String,
                       args: List[String],
                       in: InputStream,
                       out: OutputStream,
                       ctx: collection.mutable.Map[String, String]): Boolean = {
    val (xs, handlers) = GrepFlag.matchOptions(args)
    val (stream, regex) = if (xs.length == 1) {
      Stream.continually(StdIn.readLine()) -> xs.head
    } else if (xs.length >= 2) {
      Source.fromFile(xs.tail.mkString).getLines().toStream -> xs.head
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
    writer.flush()
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
              writer.write(Colors.ANSI_BLACK)
            }
            if (m.contains(i)) {
              writer.write(Colors.ANSI_RED)
            }
            writer.write(c)
        }
        writer.write(System.lineSeparator())
    }
    stream.force
  }

  final case class MatchResult(source: String, matches: SortedMap[Int, Int], toPrint: Boolean)
}
