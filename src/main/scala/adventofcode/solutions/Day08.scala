package adventofcode.solutions

import adventofcode.Definitions.*

import scala.collection.View

@main def Day08 = Day(8) { (input, part) =>

  val pairs = input.toLines.map {
    case s"$l | $r" =>
      def parse(s: String): Seq[Set[Char]] = s.split(" ").map(_.toSet).toSeq
      (parse(l), parse(r))
  }

  val digits = IndexedSeq(
    "abcefg",
    "cf",
    "acdeg",
    "acdfg",
    "bcdf",
    "abdfg",
    "abdefg",
    "acf",
    "abcdefg",
    "abcdfg",
  ).map(_.toSet)

  val identifiable = digits.map(_.size).groupBy(identity).filter(_._2.sizeIs == 1).keySet

  part(1) = pairs.unzip._2.flatMap(_.map(_.size)).count(identifiable.contains)

  val digitsSet = digits.toSet
  val chars = digitsSet.flatten.toSeq
  val assignments = chars.permutations.map(_.zip(chars).toMap).toSeq

  part(2) = pairs.map {
    case (l, r) =>
      val assignment = assignments.find(candidate => l.view.map(_.map(candidate)).forall(digitsSet.contains)).get
      r.map(_.map(assignment)).map(digits.indexOf).mkString.toInt
  }.sum

}
