package ru.jenya.cli.interpret

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

import ru.jenya.cli.interpret.envirnoment.{Env, EnvImpl}

package object command {

  def run(hander: CMD, cmd: String, args: List[String], input: String = "", ctx: Env = EnvImpl) = {
    val in = new ByteArrayInputStream(input.getBytes)
    val out = new ByteArrayOutputStream()
    val err = new ByteArrayOutputStream()
    hander.execute(cmd, args, in, out, err, ctx)
    val s = new String(out.toByteArray)
    (ctx, s)
  }
}
