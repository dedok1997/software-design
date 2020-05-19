package ru.jenya.cli.interpret.envirnoment

// Helper for context
trait Env {

  /** Get current directory */
  def currentDir(): String
  /** Get environment property */
  def get(key: String): String
  /** Set environment property */
  def set(key: String, value: String): String
  /** Get environment property with default value  */
  def getOrElse(key: String, default: String): String
}
