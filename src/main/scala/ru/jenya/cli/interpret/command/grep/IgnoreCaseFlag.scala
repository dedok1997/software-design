package ru.jenya.cli.interpret.command.grep

import java.util.regex.{Matcher, Pattern}

import ru.jenya.cli.interpret.command.grep.GrepCMD.MatchResult

object IgnoreCaseFlag extends GrepFlag {


  def beforeFound(pattern: Pattern, sources: Stream[String]): (Pattern, Stream[String]) = {
    val p = Pattern.compile(pattern.pattern(), pattern.flags() | Pattern.CASE_INSENSITIVE)
    (p, sources)
  }

  def afterFound(sources: Stream[MatchResult]): Stream[MatchResult] = sources
}
