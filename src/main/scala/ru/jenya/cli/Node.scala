package ru.jenya.cli

sealed trait Node
final case class Command(name: String, ops: List[String]) extends Node
final case class PipeLine(commands: List[Command]) extends Node
