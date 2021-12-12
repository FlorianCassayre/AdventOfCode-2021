package adventofcode.solutions

import adventofcode.Definitions.*

@main def Day12 = Day(12) { (input, part) =>

  val edges = input.toLines.map { case s"$l-$r" => l -> r }
  val graph = (edges ++ edges.map(_.swap)).groupBy((l, _) => l).view.mapValues(_.map((_, r) => r).toSet).toMap

  def isLarge(s: String): Boolean = s.forall(_.isUpper)

  val (start, end) = ("start", "end")

  def count(f: (String, Seq[String]) => Boolean, path: Seq[String] = Seq(start)): Int =
    val s = path.head
    if s == end then
      1
    else
      graph(s).filter(t => t != start && (isLarge(t) || !path.contains(t) || f(t, path))).toSeq.map(t => count(f, t +: path)).sum

  part(1) = count((_, _) => false)

  part(2) = count { (s, p) =>
    val d = p.filterNot(isLarge)
    d.distinct.size == d.size
  }

}
