package ru.jenya.cli

import java.io.File

import ru.jenya.cli.interpret.Interpreter
import ru.jenya.cli.show.CLIShowToken
import ru.jenya.cli.syntax.Syntax
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
    StdIn.readLine() match {
      case "exit" => ()
      case cmd =>
        Syntax.commands(cmd, ctx) match {
          case Right(p) =>
            Interpreter.handle(p, in, out, ctx)
            run(ctx)
          case Left(_) => ()
        }
    }
  }

  run(collection.mutable.Map.empty)

}
