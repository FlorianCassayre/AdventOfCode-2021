package adventofcode.solutions

import adventofcode.Definitions.*

import scala.collection.View

@main def Day24 = Day(24) { (input, part) =>

  val magic = input.toLines.grouped(18).map { group =>
    (group(4), group(5), group(15)) match {
      case (s"div z $a", s"add x $b", s"add y $c") => (a.toInt, b.toInt, c.toInt)
    }
  }.toIndexedSeq

  val range = 1 to 9

  def compute(input: Seq[Int]): (Int, Int) = input.zip(magic).foldLeft((0, 0)) { case ((z0, _), (i, (a, b, c))) =>
    val v = (z0 % 26) + b
    ((if v != i then (z0 / a) * 25 + i + c else 0) + (z0 / a), v)
  }

  val (_, indices) = magic.zipWithIndex.foldLeft((Seq.empty[Int], IndexedSeq.empty[Option[Int]])) { case ((stack, indices), ((a, _, _), i)) =>
    if a == 1 then
      (i +: stack, indices :+ None)
    else
      (stack.tail, indices :+ stack.headOption)
  }

  def find(range: Range): String =
    magic.indices.foldLeft(IndexedSeq.empty[Int]) { (filled, k) =>
      val newFilled = filled :+ range.head
      indices(k) match
        case Some(i) =>
          (for
            v1 <- range.view
            fillTest = newFilled.updated(i, v1)
            (_, v2) = compute(fillTest)
            (a, _, _) = magic(k)
            if a == 1 || range.contains(v2)
          yield fillTest.updated(k, v2)).head
        case None => newFilled
    }.mkString

  part(1) = find(range.reverse)

  part(2) = find(range)

}
