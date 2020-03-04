package ru.jenya.cli

sealed trait Token {

}

final case class Str(str: String) extends Token
case object Pipe extends Token
case object WhiteSpace extends Token
case object OpBr extends Token
case object ClBr extends Token
case object Quot extends Token
case class Op(str: String) extends Token
final case class SimpleStr(str: String) extends Token
final case class SubstStr(str: String) extends Token
case object EOF extends Token