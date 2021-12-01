package adventofcode.solutions

import adventofcode.Definitions.*

@main def Day01 = Day(1) { (input, part) =>

  val ns = input.toLines.map(_.toInt)

  part(1) = ns.zip(ns.tail).count(_ < _)

  val three = ns.sliding(3).map(_.sum).toSeq

  part(2) = three.zip(three.tail).count(_ < _)

}
