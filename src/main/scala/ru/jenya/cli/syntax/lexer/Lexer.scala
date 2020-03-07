package ru.jenya.cli.syntax.lexer

import ru.jenya.cli.syntax.data.Token

trait Lexer {

  def tokenize(string: List[Char]): List[Token]
}
