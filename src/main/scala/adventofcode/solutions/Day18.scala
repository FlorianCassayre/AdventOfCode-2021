package adventofcode.solutions

import adventofcode.Definitions.*
import scala.util.chaining.*

@main def Day18 = Day(18) { (input, part) =>

  enum Tree:
    case Branch(l: Tree, r: Tree)
    case Leaf(v: Int)
  import Tree.*

  def parse(s: IndexedSeq[Char]): Tree =
    def scan(i: Int, k: Int): Tree = s(i) match
      case '[' => scan(i + 1, k + 1)
      case ']' => scan(i + 1, k - 1)
      case ',' if k == 1 => Branch(parse(s.slice(1, i)), parse(s.slice(i + 1, s.length - 1)))
      case d if d.isDigit && k == 0 => Leaf(d.asDigit)
      case _ => scan(i + 1, k)
    scan(0, 0)

  def explode(t: Tree): (Tree, Boolean) =
    def accumulate(t: Tree, vl: Int, vr: Int): Tree = t match
      case Branch(l, r) => Branch(accumulate(l, vl, 0), accumulate(r, 0, vr))
      case Leaf(v) => Leaf(v + vl + vr)
    def explode(t: Tree, i: Int, exploded: Boolean): (Tree, Int, Int, Boolean) = t match
      case Branch(l: Leaf, r: Leaf) if i >= 4 && !exploded => (Leaf(0), l.v, r.v, true)
      case Branch(l, r) =>
        explode(l, i + 1, exploded).pipe((lt, ll, lr, exploded) =>
          explode(r, i + 1, exploded).pipe((rt, rl, rr, exploded) =>
            (Branch(accumulate(lt, 0, rl), accumulate(rt, lr, 0)), ll, rr, exploded)
          )
        )
      case Leaf(v) => (Leaf(v), 0, 0, exploded)
    explode(t, 0, false).pipe((t, _, _, exploded) => (t, exploded))
  def split(t: Tree, splitted: Boolean = false): (Tree, Boolean) = t match
    case Branch(l, r) => split(l, splitted).pipe((nl, splitted) => split(r, splitted).pipe((nr, splitted) => (Branch(nl, nr), splitted)))
    case Leaf(v) =>
      if v >= 10 && !splitted then
        val h = v / 2
        (Branch(Leaf(h), Leaf(v - h)), true)
      else
        (t, splitted)
  def reduce(t: Tree): Tree =
    val (exploded, hasExploded) = explode(t)
    if hasExploded then
      reduce(exploded)
    else
      val (splitted, hasSplit) = split(t)
      if hasSplit then
        reduce(splitted)
      else
        t
  def add(l: Tree, r: Tree): Tree = reduce(Branch(l, r))
  def magnitude(t: Tree): Int = t match
    case Branch(l, r) => 3 * magnitude(l) + 2 * magnitude(r)
    case Leaf(v) => v

  val list = input.toLines.map(s => parse(s.toIndexedSeq))

  part(1) = magnitude(list.reduceLeft(add))

  part(2) = list.indices.flatMap(i => list.indices.filter(i != _).map(j => magnitude(add(list(i), list(j))))).max

}
