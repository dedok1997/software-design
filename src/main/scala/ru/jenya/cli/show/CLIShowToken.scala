package ru.jenya.cli.show

import ru.jenya.cli.syntax.data._

object CLIShowToken extends CLIShow[Token] {
  def show(a: Token): String = a match {
    case Str(str: String) => str
    case Pipe => "|"
    case WhiteSpace => " "
    case OpBr => "("
    case ClBr => ")"
    case Quot => "\""
    case Op(str) => str
    case SimpleStr(str) => s"'$str'"
    case SubstStr(str) => s""""${str}""""
    case EOF => ""
  }
}
