package ru.jenya.cli.interpret.command.grep

import java.util.regex.Pattern

import org.apache.commons.cli.Options
import ru.jenya.cli.interpret.command.grep.GrepCMD.MatchResult
import org.apache.commons.cli.DefaultParser
import sun.tools.jar.CommandLine

import scala.collection.mutable.ListBuffer

/** Interface for grep flag */
trait GrepFlag {

  /** Run before searching */
  def beforeFound(regex: Pattern, sources: Stream[String]): (Pattern, Stream[String])

  /** Run after searching */
  def afterFound(sources: Stream[MatchResult]): Stream[MatchResult]

}

/** Companion object */
object GrepFlag {


  def matchOptions(args: List[String]): (List[String], List[GrepFlag]) = {

    val options = new Options
    options.addOption("A", true, "Print n lines after")
    options.addOption("w", false, "Match only full word")
    options.addOption("i", false, "Ignore case")
    val parser = new DefaultParser
    val cmd = parser.parse(options, args.toArray)
    val res = ListBuffer.empty[GrepFlag]
    if(cmd.hasOption('A')){
      val n = cmd.getOptionObject('A').toString.toInt
      res.append(new AfterFlag(n))
    }
    if(cmd.hasOption('i')){
      res.append(IgnoreCaseFlag)
    }
    if(cmd.hasOption('w')){
      res.append(WorOnlyFlag)
    }

    cmd.getArgs.toList -> res.toList
  }
}
