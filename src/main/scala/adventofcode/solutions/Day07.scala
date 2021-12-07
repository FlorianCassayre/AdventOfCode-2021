package adventofcode.solutions

import adventofcode.Definitions.*
import scala.util.chaining.*

@main def Day07 = Day(7) { (input, part) =>

  val ns = input.split(",").map(_.toInt).sorted

  part(1) = ns(ns.size / 2).pipe(x => ns.map(y => Math.abs(x - y)).sum)

  part(2) = (ns.sum / ns.size).pipe(x => ns.map(y => Math.abs(x - y).pipe(z => z * (z + 1) / 2)).sum)

}
