package adventofcode.solutions

import adventofcode.Definitions.*

@main def Day25 = Day(25) { (input, part) =>

  type Position = (Int, Int)

  case class State(east: Set[Position], south: Set[Position]):
    def contains(p: Position): Boolean = east.contains(p) || south.contains(p)

  val (n, m) = (input.toLines.size, input.toLines.head.length)

  val (east, south) = input.toLines.zipWithIndex.flatMap { case (row, i) => row.zipWithIndex.flatMap { case (v, j) => v match {
    case '.' => None
    case '>' => Some(Left((i, j)))
    case 'v' => Some(Right((i, j)))
  } } }.partitionMap(identity)

  val initialState = State(east.toSet, south.toSet)

  def step(state: State): State =
    val first = state.copy(east = state.east.map { (i, j) =>
      val next = (i, (j + 1) % m)
      if !state.contains(next) then next else (i, j)
    })
    first.copy(south = first.south.map { (i, j) =>
      val next = ((i + 1) % n, j)
      if !first.contains(next) then next else (i, j)
    })

  def fix(state: State, i: Int): Int =
    val next = step(state)
    if next == state then i else fix(next, i + 1)

  part(1) = fix(initialState, 1)

  part(2) = ""

}
