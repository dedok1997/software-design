package ru.jenya.cli.interpret.envirnoment

object EnvImpl extends Env {
  override def currentDir(): String = System.getProperty("user.dir")

  override def get(key: String): String = System.getProperty(key)
  override def set(key: String, value: String): String = System.setProperty(key, value)

  override def getOrElse(key: String, default: String): String = System.getProperty(key)
}
