package ru.jenya.cli

import ru.jenya.cli.interpret.Interpreter
import ru.jenya.cli.interpret.envirnoment.EnvImpl
import ru.jenya.cli.show.CLIShowToken
import ru.jenya.cli.syntax.Syntax
import ru.jenya.cli.syntax.lexer.LexerImpl
import ru.jenya.cli.syntax.parser.ParserImpl

import scala.annotation.tailrec
import scala.io.StdIn

/** Entry point of application */
object Appl extends App {

  implicit val show = CLIShowToken
  implicit val l = LexerImpl
  implicit val p = new ParserImpl
  val in = System.in
  val out = System.out
  val err = System.err

  @tailrec def run(): Unit = {
      Syntax.commands(StdIn.readLine(), EnvImpl) match {
        case Right(p) =>
          val result = Interpreter.handle(p, in, out, err, EnvImpl)
          if (result) run()
        case Left(_) => run()
      }
  }

  run()
}
