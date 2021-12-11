package adventofcode.solutions

import adventofcode.Definitions.*

import scala.collection.View
import scala.util.chaining.*

@main def Day11 = Day(11) { (input, part) =>

  type State = IndexedSeq[IndexedSeq[Int]]

  val initial = input.toLines.map(_.map(_.asDigit))

  def next(state: State): (State, Int) =
    def flash(state: State, flashed: Set[(Int, Int)]): (State, Int) =
      val toFlash =
        for
          i <- state.indices
          j <- state(i).indices
          t = (i, j)
          if state(i)(j) > 9 && !flashed.contains(t)
        yield t
      if toFlash.nonEmpty then
        val r = -1 to 1
        val indices =
          for
            (i, j) <- toFlash
            id <- r
            jd <- r
            i1 = i + id
            j1 = j + jd
            if state.indices.contains(i1) && state(i1).indices.contains(j1)
          yield (i1, j1)
        val newState = indices.foldLeft(state) { case (state, (i, j)) => state.updated(i, state(i).updated(j, state(i)(j) + 1)) }
        flash(newState, flashed ++ toFlash)
      else
        (state.zipWithIndex.map((row, i) => row.zipWithIndex.map((v, j) => if flashed.contains((i, j)) then 0 else v)), flashed.size)
    flash(state.map(_.map(_ + 1)), Set.empty)

  def count(state: State, n: Int, f: Int): Int =
    if n > 0 then
      val (s, f1) = next(state)
      count(s, n - 1, f + f1)
    else
      f

  part(1) = count(initial, 100, 0)

  def sync(state: State, n: Int): Int =
    if state.forall(_.forall(_ == 0)) then
      n
    else
      val (s, _) = next(state)
      sync(s, n + 1)

  part(2) = sync(initial, 0)

}
