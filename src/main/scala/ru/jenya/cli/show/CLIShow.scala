package ru.jenya.cli.show

trait CLIShow[A] {
  /** Show type class **/
  def show(a: A): String
}
