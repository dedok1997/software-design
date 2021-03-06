package ru.jenya.cli.syntax.parser

import ru.jenya.cli.syntax.data._

trait Parser {
  /** Build ast from token */
  def parse(s: List[Token]): Either[String, PipeLine]
}