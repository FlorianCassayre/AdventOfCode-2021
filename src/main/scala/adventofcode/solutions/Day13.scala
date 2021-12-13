package adventofcode.solutions

import adventofcode.Definitions.*
import scala.util.chaining.*

@main def Day13 = Day(13) { (input, part) =>

  val (points, instructions) =
    val (left, right) = input.toLines.span(_.nonEmpty)
    (left.map { case s"$x,$y" => (x.toInt, y.toInt) }.toSet, right.tail.map { case s"fold along $c=$v" => (c, v.toInt) })

  def fold(instructions: Seq[(String, Int)]): Set[(Int, Int)] =
    instructions.foldLeft(points) { case (set, (c, v)) => c match
      case "x" => set.map((x, y) => (v - (v - x).abs, y))
      case "y" => set.map((x, y) => (x, v - (v - y).abs))
    }

  part(1) = fold(instructions.take(1)).size

  part(2) = fold(instructions).pipe { set =>
    val ((minX, maxX), (minY, maxY)) = (set.map((x, _) => x).pipe(xs => (xs.min, xs.max)), set.map((_, y) => y).pipe(ys => (ys.min, ys.max)))
    (minY to maxY).map(y => (minX to maxX).map(x => if set.contains((x, y)) then "##" else "  ").mkString).mkString("\n")
  }

}
