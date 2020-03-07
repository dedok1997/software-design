package ru.jenya.cli.show

trait CLIShow[A] {
  //Show type class for A
  def show(a: A): String
}
