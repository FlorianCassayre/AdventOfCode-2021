package adventofcode.solutions

import adventofcode.Definitions.*

@main def Day03 = Day(3) { (input, part) =>

  val lines = input.toLines

  def fromBinary(s: IndexedSeq[Char]): Int = Integer.parseInt(s.mkString, 2)

  val counts = lines.transpose.map(_.groupBy(identity).view.mapValues(_.size).toMap)

  part(1) = fromBinary(counts.map(_.maxBy(_._2)._1)) * fromBinary(counts.map(_.minBy(_._2)._1))

  def iterate(list: Seq[String], index: Int, isMax: Boolean): String =
    list match
      case h +: Seq() => h
      case _ =>
        val groups = list.groupBy(_(index)).map((c, seq) => (seq.size, c) -> seq)
        val (_, seq) = if isMax then groups.maxBy(_._1) else groups.minBy(_._1)
        iterate(seq, index + 1, isMax)

  part(2) = fromBinary(iterate(lines, 0, isMax = true)) * fromBinary(iterate(lines, 0, isMax = false))

}
