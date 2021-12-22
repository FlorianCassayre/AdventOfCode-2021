package adventofcode.solutions

import adventofcode.Definitions.*

import scala.collection.immutable.BitSet

@main def Day22 = Day(22) { (input, part) =>

  case class Vec(x: Long, y: Long, z: Long):
    def pointwise(f: (Long, Long) => Long)(that: Vec): Vec = Vec(f(x, that.x), f(y, that.y), f(z, that.z))
    def >>=(that: Vec): Boolean = x >= that.x && y >= that.y && z >= that.z
    def <<=(that: Vec): Boolean = that >>= this
    def min(that: Vec): Vec = pointwise(_.min(_))(that)
    def max(that: Vec): Vec = pointwise(_.max(_))(that)

  case class AABB(min: Vec, max: Vec):
    require(min <<= max)
    def &(that: AABB): Option[AABB] =
      val (nmin, nmax) = (min.max(that.min), max.min(that.max))
      if(nmin <<= nmax) Some(new AABB(nmin, nmax)) else None

  val parsed = input.toLines.map {
    case s"$statusStr x=$x0..$x1,y=$y0..$y1,z=$z0..$z1" =>
      val status = statusStr match
        case "on" => true
        case "off" => false
      status -> AABB(Vec(x0.toInt, y0.toInt, z0.toInt), Vec(x1.toInt, y1.toInt, z1.toInt))
  }

  val r = 50
  val scope = AABB(Vec(-r, -r, -r), Vec(r, r, r))

  part(1) =
    parsed.foldLeft(Set.empty[Vec]) { case (set, (status, aabb)) =>
      val ps = for
        box <- (aabb & scope).toIndexedSeq
        x <- box.min.x to box.max.x
        y <- box.min.y to box.max.y
        z <- box.min.z to box.max.z
      yield Vec(x, y, z)
      if status then set ++ ps else set -- ps
    }.size

  val boxes = parsed.map((_, aabb) => aabb)

  def coordinates(f: Vec => Long): (IndexedSeq[Long], Map[Long, Int]) =
    val vs = boxes.flatMap(b => Seq(f(b.min), f(b.max), f(b.max) + 1)).distinct.sorted
    (vs, vs.zipWithIndex.toMap)

  val ((xs, ixs), (ys, iys), (zs, izs)) = (coordinates(_.x), coordinates(_.y), coordinates(_.z))

  def pack(p: Vec): Int = (p.x + p.y * xs.size + p.z * xs.size * ys.size).toInt

  def rangeFor(min: Long, max: Long, ivs: Map[Long, Int]): Range =
    if min == max then ivs(min) to ivs(min) else ivs(min) to (ivs(max + 1) - 1)

  val compactSpace =
    parsed.foldLeft(BitSet.empty) { case (bs, (status, aabb)) =>
      val ps = for
        x <- rangeFor(aabb.min.x, aabb.max.x, ixs)
        y <- rangeFor(aabb.min.y, aabb.max.y, iys)
        z <- rangeFor(aabb.min.z, aabb.max.z, izs)
      yield pack(Vec(x, y, z))
      if status then bs ++ ps else bs -- ps
    }

  def thickness(i: Int, seq: IndexedSeq[Long]): Long = if i == seq.size - 1 then 1 else (seq(i + 1) - 1) - seq(i) + 1

  val all =
    for {
      x <- xs.indices
      y <- ys.indices
      z <- zs.indices
      if compactSpace(pack(Vec(x, y, z)))
    } yield thickness(x, xs) * thickness(y, ys) * thickness(z, zs)

  part(2) = all.sum

}
