package ru.jenya.cli.interpret.envirnoment

object EnvUtils {

  def currentDir(m: collection.Map[String, String]) = m.getOrElse("cur_dir", "/")
}
