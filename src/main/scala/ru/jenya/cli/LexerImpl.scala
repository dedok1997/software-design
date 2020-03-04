package ru.jenya.cli

object LexerImpl extends Lexer{
    override def tokenize(string: List[Char]): List[Token] = string match{
      case Nil => List(EOF)
      case '|'::xs => Pipe::tokenize(xs)
      case ' '::xs => WhiteSpace::tokenize(xs)
      case '('::xs => OpBr::tokenize(xs)
      case ')'::xs => ClBr::tokenize(xs)
      case '='::xs => Op("=")::tokenize(xs)
      case '$'::xs => Op("$")::tokenize(xs)
      case '''::xs =>
        val str = xs.takeWhile(_!=''')
        val (_::tail) = xs.dropWhile(_!=''')
        SimpleStr(str.mkString)::tokenize(tail)
      case '"'::xs =>
        val str = xs.takeWhile(_!='"')
        val (_::tail) = xs.dropWhile(_!='"')
        SubstStr(str.mkString)::tokenize(tail)
      case xs =>
        val str = xs.takeWhile(c => Character.isAlphabetic(c) || Character.isDigit(c) || c == '.')
        val tail = xs.dropWhile(c => Character.isAlphabetic(c) || Character.isDigit(c) || c == '.')
        Str(str.mkString)::tokenize(tail)
    }
}

object Sol{

  def main(args: Array[String]): Unit = {
    println(LexerImpl.tokenize("""cat$bat 'a' "bc" | cat c d""".toList))
  }
}
