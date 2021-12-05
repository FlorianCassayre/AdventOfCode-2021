package adventofcode.solutions

import adventofcode.Definitions.*

@main def Day05 = Day(5) { (input, part) =>

  val bounds = input.toLines.map { case s"$x1,$y1 -> $x2,$y2" => (x1.toInt, y1.toInt) -> (x2.toInt, y2.toInt) }

  def count(diagonals: Boolean): Int =
    val points =
      for
        ((x1, y1), (x2, y2)) <- bounds
        (nx, ny) = (Math.abs(x1 - x2), Math.abs(y1 - y2))
        if diagonals || nx == 0 || ny == 0
        (dx, dy) = (if nx > 0 then (x2 - x1) / nx else 0, if ny > 0 then (y2 - y1) / ny else 0)
        i <- 0 to Math.max(nx, ny)
      yield (x1 + i * dx, y1 + i * dy)
    points.groupBy(identity).count((_, vs) => vs.sizeIs > 1)

  part(1) = count(false)

  part(2) = count(true)

}
