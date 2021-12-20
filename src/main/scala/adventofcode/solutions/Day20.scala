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
        val bits =
          for
            i1 <- -1 to 1
            j1 <- -1 to 1
          yield (if state(i + i1)(j + j1) then 1 else 0)
        val k = Integer.parseInt(bits.mkString, 2)
        patterns(k)
      })

  def repeat(n: Int): Int =
    val o2 = 2 * n
    val o1 = 2 * o2
    val padded = (0 until (initial.size + 2 * o1)).map(i => (0 until (initial.head.size + 2 * o1)).map(j =>
      initial.indices.contains(i - o1) && initial.head.indices.contains(j - o1) && initial(i - o1)(j - o1)))
    val result = View.iterate(padded, n + 1)(next).last
    (for
      i <- o2 until (initial.size + 2 * o1 - o2)
      j <- o2 until (initial.head.size + 2 * o1 - o2)
      if result(i)(j)
    yield 1).sum

  part(1) = repeat(2)

  part(2) = repeat(50)

}
