package adventofcode.solutions

import adventofcode.Definitions.*
import scala.util.chaining.*

@main def Day14 = Day(14) { (input, part) =>

  val lines = input.toLines
  val initial = lines.head.toIndexedSeq
  val rules = lines.tail.tail.map { case s"$l -> $r" => l.toIndexedSeq.pipe { case IndexedSeq(a, b) => (a, b) -> r.head } }.toMap

  def merge[A](a: Map[A, Long], b: Map[A, Long]): Map[A, Long] = (a.toSeq ++ b.toSeq).groupBy((k, _) => k).view.mapValues(_.map((_, v) => v).sum).toMap
  def counts[A](seq: Seq[A]): Map[A, Long] = seq.groupBy(identity).view.mapValues(_.size.toLong).toMap

  def memoized(a: Char, b: Char, state: Map[(Char, Char, Int), Map[Char, Long]], i: Int, n: Int): (Map[Char, Long], Map[(Char, Char, Int), Map[Char, Long]]) = {
    if i == n then
      (counts(Seq(a, b)), state)
    else
      state.get((a, b, i)) match
        case Some(v) => (v, state)
        case None =>
          val x = rules((a, b))
          val (left, state1) = memoized(a, x, state, i + 1, n)
          val (right, state2) = memoized(x, b, state1, i + 1, n)
          val result = merge(merge(left, right), Map(x -> -1))
          (result, state2 + ((a, b, i) -> result))
  }

  def compute(n: Int): Long =
    merge(
      initial.zip(initial.tail).map((a, b) => memoized(a, b, Map.empty, 0, n)._1).reduce(merge),
      counts(initial.init.tail).view.mapValues(-_).toMap
    ).values.pipe(vs => vs.max - vs.min)

  part(1) = compute(10)

  part(2) = compute(40)

}
