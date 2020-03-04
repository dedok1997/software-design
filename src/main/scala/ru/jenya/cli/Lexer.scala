package ru.jenya.cli

trait Lexer {

  def tokenize(string: List[Char]): List[Token]
}
