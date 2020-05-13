package ru.jenya.cli.interpret.command.grep

import java.util.regex.Pattern

import org.apache.commons.cli.{DefaultParser, Options}
import ru.jenya.cli.interpret.command.grep.GrepCMD.MatchResult

/** Implementation of -A flag from grep */
class AfterFlag(val n: Int) extends GrepFlag {


  override def beforeFound(pattern: Pattern, sources: Stream[String]): (Pattern, Stream[String]) =
    (pattern, sources)


  override def afterFound(sources: Stream[MatchResult]): Stream[MatchResult] =
    sources.scanLeft(0 -> (null: MatchResult)) {
      case (_, m) if m.toPrint => (n, m)
      case (acc, m) if acc._1 > 0 => (acc._1 - 1, m.copy(toPrint = true))
      case (_, m) => (0, m)
    }.tail.map(_._2)
}
