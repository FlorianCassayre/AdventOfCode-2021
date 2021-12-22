package adventofcode.solutions

import adventofcode.Definitions.*

import java.util

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

  def compute(seq: IndexedSeq[(Boolean, AABB)]): Long =
    val boxes = seq.map((_, aabb) => aabb)

    def coordinates(f: Vec => Long): (IndexedSeq[Long], Map[Long, Int]) =
      val vs = boxes.flatMap(b => Seq(f(b.min), f(b.max) + 1)).distinct.sorted
      (vs, vs.zipWithIndex.toMap)

    val ((xs, ixs), (ys, iys), (zs, izs)) = (coordinates(_.x), coordinates(_.y), coordinates(_.z))

    inline def pack(x: Long, y: Long, z: Long): Int = (x + y * xs.size + z * xs.size * ys.size).toInt

    inline def rangeFor(min: Long, max: Long, ivs: Map[Long, Int]): Range =
      ivs(min) until ivs(max + 1)

    // Begin side effects

    import java.util.BitSet
    val bs = new BitSet(xs.size * ys.size * zs.size)
    seq.foreach { case (status, aabb) =>
      val ps = for
        z <- izs(aabb.min.z) until izs(aabb.max.z + 1)
        y <- iys(aabb.min.y) until iys(aabb.max.y + 1)
        x <- ixs(aabb.min.x) until ixs(aabb.max.x + 1)
      do
        bs.set(pack(x, y, z), status)
    }

    inline def thickness(i: Int, seq: IndexedSeq[Long]): Long = seq(i + 1) - seq(i)

    var acc = 0L
    for
      z <- zs.indices
      y <- ys.indices
      x <- xs.indices
    do
      if bs.get(pack(x, y, z)) then
        acc += thickness(x, xs) * thickness(y, ys) * thickness(z, zs)
    acc

  val r = 50
  val scope = AABB(Vec(-r, -r, -r), Vec(r, r, r))

  part(1) = compute(parsed.flatMap { case (status, aabb) => (aabb & scope).map(status -> _) })

  part(2) = compute(parsed)

}
