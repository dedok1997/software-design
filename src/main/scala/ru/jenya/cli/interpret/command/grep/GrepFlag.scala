package ru.jenya.cli.interpret.command.grep

import java.util.regex.Pattern

import ru.jenya.cli.interpret.command.grep.GrepCMD.MatchResult

// Interface for grep flag
trait GrepFlag {

  // run before searching
  def beforeFound(regex: Pattern, sources: Stream[String]): (Pattern, Stream[String])

  // run after searching
  def afterFound(sources: Stream[MatchResult]): Stream[MatchResult]
}

object GrepFlag {

  def matchOptions(args: List[String],
                   acc: List[String] = List(),
                   handler: List[GrepFlag] = List()): (List[String], List[GrepFlag]) = args match {
    case Nil => acc.reverse -> handler
    case "-A" :: num :: xs if num.forall(_.isDigit) => matchOptions(xs, acc, (new AfterFlag(num.toInt) :: handler))
    case "-i" :: xs => matchOptions(xs, acc, IgnoreCaseFlag :: handler)
    case "-w" :: xs => matchOptions(xs, acc, WorOnlyFlag :: handler)
    case s :: xs => matchOptions(xs, s :: acc, handler)

  }
}
