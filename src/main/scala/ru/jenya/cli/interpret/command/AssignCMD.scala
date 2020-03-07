package ru.jenya.cli.interpret.command

import java.io.{InputStream, OutputStream}

object AssignCMD extends CMD {
  def execute(s: String,
              args: List[String],
              in: InputStream,
              out: OutputStream,
              ctx: collection.mutable.Map[String, String]): Unit = {
    if (args.size == 2) {
      ctx.put(args.head, args.tail.head)
    }
  }
}
