package adventofcode.solutions

import adventofcode.Definitions.*

@main def Day06 = Day(6) { (input, part) =>

  val `6` = 6
  val `8` = 8

  val initial = input.split(",").map(_.toInt).foldLeft(IndexedSeq.fill(`8` + 1)(BigInt(0)))((array, v) => array.updated(v, array(v) + 1))

  def simulate(state: IndexedSeq[BigInt], i: Int): BigInt =
    if i > 0 then simulate((state.tail :+ state.head).updated(`6`, state(`6` + 1) + state.head), i - 1) else state.sum

  part(1) = simulate(initial, 80)

  part(2) = simulate(initial, 256)

}
