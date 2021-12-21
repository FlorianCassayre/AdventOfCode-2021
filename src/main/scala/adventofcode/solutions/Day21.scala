package adventofcode.solutions

import adventofcode.Definitions.*

import scala.collection.View
import scala.util.chaining.*

@main def Day21 = Day(21) { (input, part) =>

  def parse(s: String, n: Int): Int = s match
    case s"Player $n starting position: $p" => p.toInt

  val (p1, p2) = (parse(input.toLines.head, 1), parse(input.toLines.tail.head, 2))

  case class PlayerState(position: Int, score: Int):
    def add(v: Int): PlayerState =
      val newPosition = ((position - 1 + v) % 10) + 1
      copy(position = newPosition, score = score + newPosition)
  case class State(p1: PlayerState, p2: PlayerState, turn: Boolean, die: Int):
    def scores: Seq[Int] = Seq(p1, p2).map(_.score)
    def playTurn(f: PlayerState => (PlayerState, State)): State =
      f(if turn then p1 else p2).pipe((p, s) => s.copy(p1 = if turn then p else p1, p2 = if turn then p2 else p, turn = !turn))

  val initial = State(PlayerState(p1, 0), PlayerState(p2, 0), true, 0)

  def play1(state: State): Int =
    if state.scores.max >= 1000 then
      state.scores.min * state.die
    else
      play1(state.playTurn { p =>
        val (newDie, sum) = View.iterate((state.die, 0), 3 + 1)((d, acc) => (d + 1, acc + (d % 100) + 1)).last
        (p.add(sum), state.copy(die = newDie))
      })

  part(1) = play1(initial)

  val range = 1 to 3
  val sums =
    for
      a <- range
      b <- range
      c <- range
    yield a + b + c
  val counts = sums.groupBy(identity).view.mapValues(_.size).toSeq

  def play2(state: State): (Long, Long) =
    if state.scores.max >= 21 then
      val pair = (1L, 0L)
      if state.turn then pair else pair.swap
    else
      counts.map { case (sum, count) =>
        val (s1, s2) = play2(state.playTurn(p => (p.add(sum), state)))
        (s1 * count, s2 * count)
      }.reduce { case ((s11, s21), (s12, s22)) => (s11 + s12, s21 + s22) }

  part(2) = play2(initial).pipe((a, b) => a.max(b))

}
