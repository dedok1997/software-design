package ru.jenya.cli.syntax

import ru.jenya.cli.show.CLIShow
import ru.jenya.cli.syntax.data.Token
import ru.jenya.cli.syntax.lexer.Lexer
import ru.jenya.cli.syntax.parser.Parser

object Syntax {

  def commands(str: String,
               env: collection.Map[String, String])(implicit l: Lexer,
                                                    p: Parser,
                                                    show: CLIShow[Token]) = {
    val tokens = l.tokenize(Resolver.resolve(l.tokenize(str.toList), env).mkString.toList)
    p.parse(tokens)
  }
}