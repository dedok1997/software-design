package ru.jenya.cli

trait Parser {
  def parse(s: List[Token]): Either[String, List[Node]]
}
