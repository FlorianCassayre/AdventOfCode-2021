package adventofcode.solutions

import adventofcode.Definitions.*
import scala.util.chaining.*

@main def Day07 = Day(7) { (input, part) =>

  val ns = input.split(",").map(_.toInt).sorted

  part(1) = ns.map(ns(ns.size / 2) - _ pipe(_.abs)).sum

  part(2) = (ns.head to ns.last).map(x => ns.map(x - _ pipe(_.abs) pipe(y => y * (y + 1) / 2)).sum).min

}
