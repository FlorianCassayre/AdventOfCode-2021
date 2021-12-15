package adventofcode.solutions

import adventofcode.Definitions.*

@main def Day15 = Day(15) { (input, part) =>

  def shortestPathDijkstra[U, N](adjacency: U => Set[(U, N)], source: U, earlyStopping: Set[U] = Set.empty[U])(implicit ev: Numeric[N]): (Map[U, N], Map[U, U]) = {
    import ev._
    import scala.collection.immutable.TreeSet
    def search(queue: TreeSet[(U, (N, Int))], distances: Map[U, N], predecessors: Map[U, U], visited: Set[U], k: Int): (Map[U, N], Map[U, U]) = {
      queue.headOption match {
        case Some((u, (du, _))) =>
          if(earlyStopping.contains(u)) {
            (distances, predecessors)
          } else if(!visited.contains(u)) {
            val newVisited = visited + u
            val edges = adjacency(u).filter { case (b, w) => !visited.contains(b) && (!distances.contains(b) || distances(b) > du + w) }
            val (newQueue, newDistances, newPredecessors, newK) = edges.foldLeft((queue.tail, distances, predecessors, k)) { case ((currentQueue, currentDistances, currentPredecessors, currentK), (v, w)) =>
              val newDistance = du + w
              (currentQueue + (v -> (newDistance, currentK)),
                currentDistances - v + (v -> newDistance),
                currentPredecessors + (v -> u),
                currentK + 1)
            }
            search(newQueue, newDistances, newPredecessors, newVisited, newK)
          } else {
            search(queue.tail, distances, predecessors, visited, k)
          }
        case None => (distances, predecessors)
      }
    }
    search(TreeSet((source, (zero, 0)))(Ordering.by(_._2)), Map(source -> zero), Map.empty, Set.empty, 1)
  }

  case class Position(x: Int, y: Int):
    def +(that: Position): Position = Position(x + that.x, y + that.y)
    def adjacent: Seq[Position] = Seq((-1, 0), (1, 0), (0, -1), (0, 1)).map((x1, y1) => Position(x + x1, y + y1))

  val map: IndexedSeq[IndexedSeq[Int]] = input.toLines.map(_.map(_.asDigit))
  val (w, h) = (map.head.size, map.size)

  def shortestPath(map: IndexedSeq[IndexedSeq[Int]]): Int =
    val (w, h) = (map.head.size, map.size)
    def adjacency(p: Position): Set[(Position, Int)] = p.adjacent.filter(p => p.x >= 0 && p.x < w && p.y >= 0 && p.y < h).map(p => p -> map(p.y)(p.x)).toSet
    shortestPathDijkstra(adjacency, Position(0, 0))._1(Position(w - 1, h - 1))

  part(1) = shortestPath(map)

  val k = 5
  val tiling = (0 until (k * h)).map(y => (0 until (k * w)).map(x => ((map(y % h)(x % w) + x / w + y / w - 1) % 9) + 1))

  part(2) = shortestPath(tiling)

}
