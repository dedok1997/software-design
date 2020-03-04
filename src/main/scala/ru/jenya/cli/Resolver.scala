package ru.jenya.cli

trait Resolver {

  def resolve(l: List[Token]): List[Token] = l match {
    case Str(s1)::Str(s2)::xs =>
    Str(s1++s2)::resolve(xs)
    case Nil => Nil
    case x::xs => x::resolve(xs)
  }

  def resolve(l: List[Token], env: Map[String, String]): List[Token] = l match {
    case SubstStr(s)::xs =>
      resolveString(s.toList, env)::resolve(xs, env)
    case Op("$")::OpBr::Str(key)::ClBr::xs =>
      Str(env.getOrElse(key, ""))::resolve(xs, env)
    case Op("$")::Str(key)::xs =>
      Str(env.getOrElse(key, ""))::resolve(xs, env)
    case x::xs => x::resolve(xs, env)
    case Nil => Nil
  }

  def resolveString(l: List[Char], env: Map[String, String]): List[Char] = l match {
    case Nil => Nil
    case '$'::'('::xs =>
      val key = xs.takeWhile(_!=')').mkString
      val (_::tail) = xs.dropWhile(_!=')')
      env.getOrElse(key, "").toList ++ resolveString(tail, env)
    case '$'::xs =>
      val key = xs.takeWhile(c => Character.isAlphabetic(c) || Character.isDigit(c)).mkString
      val (_::tail) = xs.dropWhile(c => Character.isAlphabetic(c) || Character.isDigit(c))
      env.getOrElse(key, "").toList ++ resolveString(tail, env)

  }
}
