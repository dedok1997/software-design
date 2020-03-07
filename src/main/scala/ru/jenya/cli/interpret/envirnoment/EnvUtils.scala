package ru.jenya.cli.interpret.envirnoment

// Helper for context
object EnvUtils {

  def currentDir(m: collection.Map[String, String]) = m.getOrElse("cur_dir", "/")
}
