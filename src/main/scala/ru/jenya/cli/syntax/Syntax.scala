package ru.jenya.cli.syntax

import ru.jenya.cli.interpret.envirnoment.Env
import ru.jenya.cli.show.CLIShow
import ru.jenya.cli.syntax.data.{PipeLine, Token}
import ru.jenya.cli.syntax.lexer.Lexer
import ru.jenya.cli.syntax.parser.Parser

object Syntax {

  /** Parse command from string*/
  def commands(str: String,
               env: Env)(implicit l: Lexer,
                         p: Parser,
                         show: CLIShow[Token]): Either[String, PipeLine] = {
    val tokens = l.tokenize(Resolver.resolve(l.tokenize(str.toList), env).mkString.toList)
    p.parse(tokens)
  }
}