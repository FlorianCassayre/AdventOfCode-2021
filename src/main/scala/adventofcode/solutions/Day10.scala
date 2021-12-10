package adventofcode.solutions

import adventofcode.Definitions.*

@main def Day10 = Day(10) { (input, part) =>

  val (closing, costError, costComplete) =
    val data = Map(
      '(' -> (')', 3, 1),
      '[' -> (']', 57, 2),
      '{' -> ('}', 1197, 3),
      '<' -> ('>', 25137, 4)
    )
    (data.map { case (o, (c, _, _)) => o -> c }, data.map { case (_, (c, s, _)) => c -> s }, data.map { case (_, (c, _, s)) => c -> s })

  def parse(seq: Seq[Char], stack: Seq[Char] = Seq.empty): Either[Char, Seq[Char]] =
    seq match
      case head +: tail =>
        if closing.contains(head) then
          parse(tail, closing(head) +: stack)
        else
          if stack.head == head then parse(tail, stack.tail) else Left(head)
      case _ => Right(stack)

  val (corrupted, incomplete) = input.toLines.map(parse(_)).partitionMap(identity)

  part(1) = corrupted.map(costError).sum

  val scores = incomplete.map(_.foldLeft(0L)(_ * 5 + costComplete(_))).sorted

  part(2) = scores(scores.size / 2)

}
