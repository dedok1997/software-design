package ru.jenya.cli


class ParserImpl(val tokens: Array[Token]) extends Parser {

  var currentToken = 0

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

  def parseStr() =  tokens(currentToken) match {
    case Str(s) =>
      currentToken = currentToken + 1
      s
    case SimpleStr(s) =>
      currentToken = currentToken + 1
      s
    case SubstStr(s) =>
      currentToken = currentToken + 1
      s
    case _ => throw new Exception()
  }

  def parseCommand() = {
    val cmd = parseStr()
    val arr = scala.collection.mutable.ArrayBuffer.empty[String]
    while (!isEOF() || !isPipe()){
      if(isWhiteSpace()) skipWhiteSpaces()
      arr.append(parseStr())
      if(isWhiteSpace()) skipWhiteSpaces()
    }
    Command(cmd, arr.toList)
  }

  def parsePipeLine() = {
    val cmd = parseCommand()
    val arr = scala.collection.mutable.ArrayBuffer.empty[Command]
    arr.append(cmd)
    while (isPipe()){
      skipPipe()
      arr.append(parseCommand())
    }
    PipeLine(arr.toList)
  }

//  def parseWhitespaces(l: List[Token]) = l match {
//    case WhiteSpace::xs =>
//      val tail = xs.dropWhile(_.isInstanceOf[WhiteSpace.type])
//      Right(() -> tail)
//    case _ => Left()
//  }
//
//  def parseCommand(s: List[Token]) = s match {
//    case Str(s)::xs =>
//      for{
//        tuple <- parseWhitespaces(xs)
//        (_, t) = tuple
//
//      }
//  }
//
//
//  def parseOpts(s: List[Token]) = s match{
//    case Str(s)::WhiteSpace::xs =>
//      for{
//
//      }
//  }



//
//  def parse(s: List[Token]): List[Node] = {
//
//  }

}
