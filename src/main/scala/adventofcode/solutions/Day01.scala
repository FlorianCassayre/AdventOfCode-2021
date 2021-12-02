package adventofcode.solutions

import adventofcode.Definitions.*

@main def Day01 = Day(1) { (input, part) =>

  val ns = input.toLines.map(_.toInt)

  part(1) = ns.zip(ns.tail).count(_ < _)

  part(2) = ns.zip(ns.drop(3)).count(_ < _)

}
