package adventofcode.solutions

import adventofcode.Definitions.*

@main def Day06 = Day(6) { (input, part) =>

  val initial = input.split(",").map(BigInt.apply).groupBy(identity).view.mapValues(s => BigInt(s.size)).toSeq

  def simulate(state: Seq[(BigInt, BigInt)], i: Int): BigInt =
    if i > 0 then
      simulate(
        state.flatMap((v, n) => if v > 0 then Seq((v - 1, n)) else Seq((BigInt(6), n), (BigInt(8), n)))
          .groupBy((v, _) => v).view.mapValues(_.map((_, n) => n).sum).toSeq,
        i - 1)
    else
      state.map((_, n) => n).sum

  part(1) = simulate(initial, 80)

  part(2) = simulate(initial, 256)

}
