package adventofcode.solutions

import adventofcode.Definitions.*

@main def Day04 = Day(4) { (input, part) =>

  val lines = input.toLines
  val numbers = lines.head.split(",").toIndexedSeq.map(_.toInt)
  val n = 5
  val grids = lines.tail.grouped(n + 1).toIndexedSeq.map(_.tail.map(_.trim.split(" +").toIndexedSeq.map(_.toInt)))

  case class Grid(state: IndexedSeq[IndexedSeq[Boolean]], grid: IndexedSeq[IndexedSeq[Int]]):
    def mark(v: Int): Grid = copy(state = grid.zip(state).map((gr, sr) => gr.zip(sr).map((g, s) => s || g == v)))
    def unmarked: Int = grid.zip(state).flatMap((gr, sr) => gr.zip(sr).filter((_, s) => !s).map((g, _) => g)).sum
    def isComplete: Boolean = state.exists(_.forall(identity)) || state.transpose.exists(_.forall(identity))

  val initial = grids.map(grid => Grid(grid.map(_.map(_ => false)), grid))

  def drawUntilWinner(remaining: Seq[Int], states: Seq[Grid]): Int =
    val v = remaining.head
    val newStates = states.map(_.mark(v))
    newStates.find(_.isComplete) match
      case Some(complete) => complete.unmarked * v
      case None => drawUntilWinner(remaining.tail, newStates)

  part(1) = drawUntilWinner(numbers, initial)

  def drawToLastWinner(remaining: Seq[Int], states: Seq[Grid], winner: Option[Int]): Int = remaining match
    case v +: tail =>
      val newStates = states.map(_.mark(v))
      val newWinner = states.zip(newStates).find((previous, current) => !previous.isComplete && current.isComplete) match
        case Some((_, complete)) => Some(complete.unmarked * v)
        case None => winner
      drawToLastWinner(tail, newStates, newWinner)
    case _ => winner.get

  part(2) = drawToLastWinner(numbers, initial, None)

}
