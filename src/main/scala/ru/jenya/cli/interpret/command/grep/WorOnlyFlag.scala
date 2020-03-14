package ru.jenya.cli.interpret.command.grep

import java.util.regex.Pattern

import ru.jenya.cli.interpret.command.grep.GrepCMD.MatchResult

object WorOnlyFlag extends GrepFlag {


  def beforeFound(pattern: Pattern, sources: Stream[String]): (Pattern, Stream[String]) = {
    (pattern, sources)
  }

  def afterFound(sources: Stream[MatchResult]): Stream[MatchResult] = sources.map {
    case MatchResult(source, matches, toPrint) =>
      val newToPrint = matches.foldLeft(toPrint) { case (acc, (begin, end)) =>
        val startSymb = if (begin > 0) source.charAt(begin) else ' '
        val endSymb = if (end < source.length - 1) source.charAt(end) else ' '
        acc && !((Character.isAlphabetic(startSymb) | Character.isDigit(startSymb))) &&
          !((Character.isAlphabetic(endSymb) | Character.isDigit(endSymb)))

      }
      MatchResult(source, matches, newToPrint)
  }
}
