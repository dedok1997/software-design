package ru.jenya.cli.interpret.command

import java.io.{InputStream, OutputStream}

import ru.jenya.cli.interpret.envirnoment.Env

/** Assign command executor */
object AssignCMD extends CMD {
  def execute(s: String,
              args: List[String],
              in: InputStream,
              out: OutputStream,
              err: OutputStream,
              ctx: Env) = {
    if (args.size == 2) {
      ctx.set(args.head, args.tail.head)
      true
    } else false
  }
}
