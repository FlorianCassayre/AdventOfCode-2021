package adventofcode.solutions

import adventofcode.Definitions.*
import scala.util.chaining.*

@main def Day09 = Day(9) { (input, part) =>

  val map = input.toLines.map(_.map(_.asDigit))

  type Position = (Int, Int)

  extension (m: map.type)
    def apply(p: Position): Int = p.pipe(m(_)(_))
    def has(p: Position): Boolean = p.pipe(m.indices.contains(_) && m.head.indices.contains(_))
    def adjacentOf(p: Position): Seq[Position] =
      Seq((-1, 0), (1, 0), (0, -1), (0, 1)).map((i1, j1) => p.pipe((i, j) => (i + i1, j + j1))).filter(m.has)

  val sources =
    for
      i <- map.indices
      j <- map.head.indices
      p = (i, j)
      if map.adjacentOf(p).forall(map(p) < map(_))
    yield p

  part(1) = sources.map(map(_) + 1).sum

  def bfs(visit: Set[Position], seen: Set[Position]): Set[Position] =
    if visit.nonEmpty then
      val next = visit.flatMap(map.adjacentOf(_).filter(map(_) < 9).filterNot(seen.contains))
      bfs(next, seen ++ next)
    else
      seen

  part(2) = sources.map(s => bfs(Set(s), Set(s)).size).sortBy(-_).take(3).product

}
