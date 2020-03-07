package ru.jenya.cli.syntax.data

sealed trait Node

final case class Command(cmd: String, ops: List[String]) extends Node

final case class PipeLine(commands: List[Command]) extends Node
