package ru.jenya.cli

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import ru.jenya.cli.interpret.Interpreter
import ru.jenya.cli.interpret.envirnoment.EnvImpl
import ru.jenya.cli.show.{CLIShow, CLIShowToken}
import ru.jenya.cli.syntax.Syntax
import ru.jenya.cli.syntax.data.Token
import ru.jenya.cli.syntax.lexer.{Lexer, LexerImpl}
import ru.jenya.cli.syntax.parser.{Parser, ParserImpl}
import utest._

object ApplicationTest extends TestSuite {
  implicit val show: CLIShow[Token] = CLIShowToken
  implicit val l: Lexer = LexerImpl
  implicit val p: Parser = new ParserImpl
  val in = new ByteArrayInputStream(Array())
  val out = new ByteArrayOutputStream()
  val err = new ByteArrayOutputStream()

  val ctx = EnvImpl
  val tests = Tests {
    test("echo a b c") {
      val res = Syntax.commands("echo a b c", ctx)
      res.isRight ==> true
      val pipe = res.getOrElse(???)
      Interpreter.handle(pipe, in, out, err, ctx)
      assert(out, "a b c\n")
    }
  }

  def assert(out: ByteArrayOutputStream, expected: String): Unit = {
    new String(out.toByteArray) ==> expected
  }
}
