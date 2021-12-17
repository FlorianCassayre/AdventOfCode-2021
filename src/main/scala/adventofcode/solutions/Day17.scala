package adventofcode.solutions

import adventofcode.Definitions.*

@main def Day17 = Day(17) { (input, part) =>

  case class Vec(x: Int, y: Int):
    def in(aabb: AABB): Boolean = x >= aabb.min.x && x <= aabb.max.x && y >= aabb.min.y && y <= aabb.max.y
    def +(that: Vec): Vec = Vec(x + that.x, y + that.y)

  case class AABB(min: Vec, max: Vec)

  val target = input match
    case s"target area: x=$x0..$x1, y=$y0..$y1" => AABB(Vec(x0.toInt, y0.toInt), Vec(x1.toInt, y1.toInt))

  def step(p: Vec, v: Vec): (Vec, Vec) = (p + v, v + Vec(-Integer.signum(v.x), -1))

  def reaches(p: Vec, v: Vec, maxY: Int = 0): Option[Int] =
    if p in target then Some(maxY)
    else if v.y < 0 && p.y < target.min.y then None
    else
      val (np, nv) = step(p, v)
      reaches(np, nv, maxY.max(np.y))

  val o = Vec(0, 0)

  val k = 200
  val ys = for y <- -k to k; x <- -k to k; v = Vec(x, y); m <- reaches(o, v) yield m

  part(1) = ys.max

  part(2) = ys.size

}
