package ru.jenya.cli.show

trait CLIShow[A] {
  def show(a: A): String
}
