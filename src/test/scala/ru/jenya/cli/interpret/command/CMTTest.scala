package ru.jenya.cli.interpret.command

import java.io.ByteArrayOutputStream

import utest._

object CMTTest extends TestSuite {
  val tests = Tests {
    test("echo") {
      val (_, s) = run(EchoCMD, "echo", List("a", "b", "c"))
      s ==> "a b c\n"
      val (_, s1) = run(EchoCMD, "echo", List())
      s1 ==> "\n"
    }
    test("assign") {
      val (ctx, "") = run(AssignCMD, "=", List("a", "b"))
      ctx("a") ==> "b"
    }
    test("pwd") {
      val (_, cur) = run(PWDCMD, "pwd", List())
      cur ==> "./"
      val (_, cur1) = run(PWDCMD, "pwd", List(), ctx = Map(PWDCMD.currentDir -> "/home/"))
      cur1 ==> "/home/"
    }

  }
}