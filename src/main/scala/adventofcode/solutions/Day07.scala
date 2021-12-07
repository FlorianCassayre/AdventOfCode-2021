package adventofcode.solutions

import adventofcode.Definitions.*
import scala.util.chaining.*

@main def Day07 = Day(7) { (input, part) =>

  val ns = input.split(",").map(_.toInt)
  val range = (ns.min to ns.max)

  part(1) = range.map(v => ns.map(x => Math.abs(v - x)).sum).min

  part(2) = range.map(v => ns.map(x => Math.abs(v - x).pipe(y => y * (y + 1) / 2)).sum).min

}
