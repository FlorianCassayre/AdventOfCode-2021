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

    val seqReversed = seq.reverse
    val values =
      for
        z <- zs.indices.view
        zv = zs(z)
        filteredZ = seqReversed.filter((_, aabb) => zv >= aabb.min.z && zv <= aabb.max.z)
        if filteredZ.exists(_._1)
        y <- ys.indices.view
        yv = ys(y)
        filteredY = filteredZ.filter((_, aabb) => yv >= aabb.min.y && yv <= aabb.max.y)
        if filteredY.exists(_._1)
        x <- xs.indices.view
        xv = xs(x)
        if filteredY.find((b, aabb) => xv >= aabb.min.x && xv <= aabb.max.x).exists(_._1)
      yield
        (xs(x + 1) - xv) * (ys(y + 1) - yv) * (zs(z + 1) - zv)

    values.sum

  val r = 50
  val scope = AABB(Vec(-r, -r, -r), Vec(r, r, r))

  part(1) = compute(parsed.flatMap { case (status, aabb) => (aabb & scope).map(status -> _) })

  part(2) = compute(parsed)

}
