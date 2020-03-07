package ru.jenya.cli

import java.io.File

import ru.jenya.cli.interpret.Interpreter
import ru.jenya.cli.show.CLIShowToken
import ru.jenya.cli.syntax.Syntax
import ru.jenya.cli.syntax.data.{Command, PipeLine}
import ru.jenya.cli.syntax.lexer.LexerImpl
import ru.jenya.cli.syntax.parser.ParserImpl

import scala.io.{Source, StdIn}

object Appl extends App {

  implicit val show = CLIShowToken
  implicit val l = LexerImpl
  implicit val p = new ParserImpl
  val in = System.in
  val out = System.out

  def run(ctx: collection.mutable.Map[String, String]): Unit = {
    Syntax.commands(StdIn.readLine(), ctx) match {
      case Right(p) =>
        val result = Interpreter.handle(p, in, out, ctx)
        if (result) run(ctx)
      case Left(_) => ()
    }
  }

  run(collection.mutable.Map.empty)

}