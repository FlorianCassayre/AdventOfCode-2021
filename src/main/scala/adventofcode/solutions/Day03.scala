package adventofcode.solutions

import adventofcode.Definitions.*

@main def Day03 = Day(3) { (input, part) =>

  val lines = input.toLines

  def fromBinary(s: IndexedSeq[Char]): Int = Integer.parseInt(s.mkString, 2)

  part(1) = lines.transpose.map(_.groupBy(identity).toIndexedSeq.sortBy(_._2.size).map(_._1)).transpose.map(fromBinary).product

  def iterate(list: Seq[String], index: Int, j: Int): IndexedSeq[Char] =
    list match
      case Seq(h) => h
      case _ => iterate(list.groupBy(_(index)).map((c, seq) => (seq.size, c) -> seq).toSeq.sortBy(_._1).map(_._2)(j), index + 1, j)

  part(2) = (0 until 2).map(iterate(lines, 0, _)).map(fromBinary).product

}
