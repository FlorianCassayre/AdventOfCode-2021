package adventofcode.solutions

import adventofcode.Definitions.*
import scala.util.chaining.*

@main def Day14 = Day(14) { (input, part) =>

  val lines = input.toLines
  val initial = lines.head.toIndexedSeq
  val rules = lines.tail.tail.map { case s"$l -> $r" => l.toIndexedSeq.pipe { case IndexedSeq(a, b) => (a, b) -> r.head } }.toMap

  extension [A](self: Map[A, Long])
    def #++(that: Map[A, Long]): Map[A, Long] = (self.toSeq ++ that.toSeq).groupBy((k, _) => k).view.mapValues(_.map((_, v) => v).sum).toMap
    def #-(that: A): Map[A, Long] = self + (that -> (self(that) - 1))

  type Memoized = Map[(Char, Char, Int), Map[Char, Long]]

  def compute(n: Int): Long =
    def divideAndConquer(a: Char, b: Char, state: Memoized, i: Int): (Map[Char, Long], Memoized) =
      if i == n then
        (Map(a -> 1L) #++ Map(b -> 1L), state)
      else
        state.get((a, b, i)) match
          case Some(v) => (v, state)
          case None =>
            val x = rules((a, b))
            divideAndConquer(a, x, state, i + 1)
              .pipe((left, state) => divideAndConquer(x, b, state, i + 1).pipe((right, state) => (left #++ right #- x, state)))
              .pipe((result, state) => (result, state + ((a, b, i) -> result)))

    initial.zip(initial.tail).scanLeft((Map.empty[Char, Long], Map.empty: Memoized)) {
      case ((_, state), (a, b)) => divideAndConquer(a, b, Map.empty, 0)
    }.unzip.pipe((result, _) => initial.init.tail.foldLeft(result.reduce(_ #++ _))(_ #- _).values.pipe(vs => vs.max - vs.min))

  part(1) = compute(10)

  part(2) = compute(40)

}
