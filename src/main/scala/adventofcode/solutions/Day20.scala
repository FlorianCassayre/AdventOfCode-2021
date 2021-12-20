package adventofcode.solutions

import adventofcode.Definitions.*
import scala.collection.View

@main def Day20 = Day(20) { (input, part) =>

  def parse(s: String): IndexedSeq[Boolean] = s.map {
    case '#' => true
    case '.' => false
  }

  val patterns = parse(input.toLines.head)
  val initial = input.toLines.tail.tail.map(parse)

  def next(state: IndexedSeq[IndexedSeq[Boolean]]): IndexedSeq[IndexedSeq[Boolean]] =
    state.indices.tail.init.map(i =>
      state(i).indices.tail.init.map { j =>
        val range = -1 to 1
        val bits =
          for
            i1 <- range
            j1 <- range
          yield (if state(i + i1)(j + j1) then 1 else 0)
        patterns(Integer.parseInt(bits.mkString, 2))
      })

  def repeat(n: Int): Int =
    val offset = 2 * n
    def pad(array: IndexedSeq[IndexedSeq[Boolean]], k: Int): IndexedSeq[IndexedSeq[Boolean]] =
      (0 until (array.size + 2 * k)).map(i => (0 until (array.head.size + 2 * k)).map(j =>
        array.indices.contains(i - k) && array.head.indices.contains(j - k) && array(i - k)(j - k)
      ))
    pad(View.iterate(pad(initial, 2 * offset), n + 1)(next).last, -offset).flatten.count(identity)

  part(1) = repeat(2)

  part(2) = repeat(50)

}
