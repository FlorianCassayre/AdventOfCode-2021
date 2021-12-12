package adventofcode.solutions

import adventofcode.Definitions.*
import scala.util.chaining.*

@main def Day12 = Day(12) { (input, part) =>

  val graph = input.toLines.flatMap { case s"$l-$r" => Seq(l -> r, r -> l) }.groupMap((l, _) => l)((_, r) => r)

  def isLarge(s: String): Boolean = s.forall(_.isUpper)

  val (start, end) = ("start", "end")

  def count(f: Seq[String] => Boolean, path: Seq[String] = Seq(start)): Int =
    val s = path.head
    if s == end then
      1
    else
      graph(s).filter(t => t != start && (isLarge(t) || !path.contains(t) || f(path))).map(t => count(f, t +: path)).sum

  part(1) = count(_ => false)

  part(2) = count(_.filterNot(isLarge).pipe(d => d.distinct.size == d.size))

}
