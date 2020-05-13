package ru.jenya.cli.syntax.lexer

import ru.jenya.cli.syntax.data.Token

trait Lexer {
  /** Build tokens from input string */
  def tokenize(string: List[Char]): List[Token]
}
