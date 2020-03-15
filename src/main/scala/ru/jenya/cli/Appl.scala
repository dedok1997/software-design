package ru.jenya.cli

import ru.jenya.cli.interpret.Interpreter
import ru.jenya.cli.show.CLIShowToken
import ru.jenya.cli.syntax.Syntax
import ru.jenya.cli.syntax.lexer.LexerImpl
import ru.jenya.cli.syntax.parser.ParserImpl

import scala.io.StdIn

object Appl extends App {

  implicit val show = CLIShowToken
  implicit val l = LexerImpl
  implicit val p = new ParserImpl
  val in = System.in
  val out = System.out
  val err = System.err

  def run(ctx: collection.mutable.Map[String, String]): Unit = {
    Syntax.commands(StdIn.readLine(), ctx) match {
      case Right(p) =>
        val result = Interpreter.handle(p, in, out, err, ctx)
        if (result) run(ctx)
      case Left(_) => ()
    }
  }

  run(collection.mutable.Map.empty)
}
