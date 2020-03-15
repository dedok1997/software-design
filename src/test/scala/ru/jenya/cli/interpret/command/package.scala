package ru.jenya.cli.interpret

import java.io.{ByteArrayInputStream, ByteArrayOutputStream}

package object command {

  def run(hander: CMD, cmd: String, args: List[String], input: String = "", ctx: Map[String, String] = Map()) = {
    val in = new ByteArrayInputStream(input.getBytes)
    val out = new ByteArrayOutputStream()
    val err = new ByteArrayOutputStream()
    val ctx1 = collection.mutable.Map(ctx.toSeq: _*)
    hander.execute(cmd, args, in, out, err, ctx1)
    val s = new String(out.toByteArray)
    (ctx1.toMap, s)
  }
}
