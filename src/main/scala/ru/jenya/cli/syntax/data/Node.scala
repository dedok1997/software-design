package ru.jenya.cli.syntax.data

final case class Command(cmd: String, ops: List[String])

final case class PipeLine(commands: List[Command])
