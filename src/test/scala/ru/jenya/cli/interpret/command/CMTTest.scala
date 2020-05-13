package ru.jenya.cli.interpret.command

import java.io.PrintWriter
import java.nio.file.{Files, Path}

import ru.jenya.cli.interpret.command.grep.GrepCMD
import ru.jenya.cli.interpret.envirnoment.EnvImpl
import utest._

object CMTTest extends TestSuite {

  def tempFile[A](content: String)(f: Path => A): A = {
    val tempFile = Files.createTempFile("a", "b")
    try {
      new PrintWriter(tempFile.toFile) {
        try {
          println(content)
        } finally {
          close()
        }
      }
      f(tempFile)
    }
    finally {
      tempFile.toFile.delete()
    }
  }

  val tests = Tests {
    test("echo") {
      val (_, s) = run(EchoCMD, "echo", List("a", "b", "c"))
      s ==> "a b c\n"
      val (_, s1) = run(EchoCMD, "echo", List())
      s1 ==> "\n"
    }
    test("assign") {
      val (ctx, "") = run(AssignCMD, "=", List("a", "b"))
      ctx.get("a") ==> "b"
    }
    test("pwd") {
      val (_, cur) = run(PWDCMD, "pwd", List())
      cur.trim ==> System.getProperty("user.dir")
      EnvImpl.set("user.dir", "/home/")
      val (_, cur1) = run(PWDCMD, "pwd", List(), ctx = EnvImpl)
      cur1.trim ==> "/home/"
    }
    test("wc") {
      val text = "abs" ++ System.lineSeparator() ++ "bc" ++ " dc" ++ System.lineSeparator() ++ "dc fg"
      tempFile(text) { tempFile =>
        val (_, cur) = run(WCCMD, "wc", List(tempFile.toString))
        val List(bytes, word, lines) = cur.split(" ").take(3).map(_.toInt).toList
        bytes ==> text.getBytes.length + 1
        lines ==> 3
        word ==> 5
      }
    }
    test("cat") {
      val text = "abs" ++ System.lineSeparator() ++ "bc" ++ "dc" ++ System.lineSeparator() ++ "dc fg"
      tempFile(text) { tempFile =>
        val (_, cur) = run(CatCMD, "cat", List(tempFile.toString))
        text ==> cur.trim
      }
    }
    test("grep") {
      val nl = System.lineSeparator()
      val text = s"a $nl b c d e f $nl gh"
      tempFile(text) { tempFile =>
        val (_, cur) = run(GrepCMD, "grep", List("В", tempFile.toString))
        cur ==> "";
        val (_, cur1) = run(GrepCMD, "grep", List("b", tempFile.toString))
        assert(cur1 != "")
        val (_, cur2) = run(GrepCMD, "grep", List("-i", "B", tempFile.toString))
        assert(cur2 != "")
        val (_, cur3) = run(GrepCMD, "grep", List("-A", "1", "b", tempFile.toString))
        val line = cur3.split(nl)(1)
        line ==> s" gh"
        val (_, cur4) = run(GrepCMD, "grep", List("-w", "g", tempFile.toString))
        cur4 ==> ""
        val (_, cur5) = run(GrepCMD, "grep", List("-w", "gh", tempFile.toString))
        assert(cur5 != "")
      }
    }
  }
}