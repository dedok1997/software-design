package ru.jenya.cli.syntax.parser

import ru.jenya.cli.syntax.data._

import scala.util.Try

class ParserImpl extends Parser {

  var currentToken = 0
  var tokens: Array[Token] = _

  def isOp(op: String) = tokens(currentToken) match {
    case Op(`op`) => true
    case _ => false
  }

  def isWhiteSpace() = tokens(currentToken) match {
    case WhiteSpace => true
    case _ => false
  }

  def isEOF() = tokens(currentToken) match {
    case EOF => true
    case _ => false
  }

  def isPipe() = tokens(currentToken) match {
    case Pipe => true
    case _ => false
  }

  def skipPipe() = if (isPipe()) currentToken = currentToken + 1

  def skipWhiteSpaces() = while (isWhiteSpace()) currentToken = currentToken + 1

  def parseOp(op: String) = {
    val Op(`op`) = tokens(currentToken)
    currentToken = currentToken + 1
    Op(op)
  }

  def parseStr() = tokens(currentToken) match {
    case Str(s) =>
      currentToken = currentToken + 1
      s
    case SimpleStr(s) =>
      currentToken = currentToken + 1
      s
    case SubstStr(s) =>
      currentToken = currentToken + 1
      s
    case a => throw new Exception(a.toString)
  }

  def parseCommand() = {
    val cmd = parseStr()
    if (isOp("=")) {
      val op = parseOp("=")
      val value = parseStr()
      Command("=", List(cmd, value))
    } else {
      val arr = scala.collection.mutable.ArrayBuffer.empty[String]

      while (!isEOF() && !isPipe()) {
        if (isWhiteSpace()) skipWhiteSpaces()
        arr.append(parseStr())
        if (isWhiteSpace()) skipWhiteSpaces()
      }

      Command(cmd, arr.toList)
    }
  }

  def parsePipeLine() = {
    val cmd = parseCommand()
    val arr = scala.collection.mutable.ArrayBuffer.empty[Command]
    arr.append(cmd)
    while (isPipe()) {
      skipPipe()
      if (isWhiteSpace()) skipWhiteSpaces()
      arr.append(parseCommand())
      if (isWhiteSpace()) skipWhiteSpaces()
    }
    PipeLine(arr.toList)
  }

  def parse(s: List[Token]): Either[String, PipeLine] = {
    tokens = s.toArray
    currentToken = 0
    Try {
      parsePipeLine()
    }.toEither.left.map(_.toString)
  }
}
