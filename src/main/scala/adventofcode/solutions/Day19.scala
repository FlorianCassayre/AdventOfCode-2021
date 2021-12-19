package adventofcode.solutions

import adventofcode.Definitions.*
import scala.collection.View

@main def Day19 = Day(19) { (input, part) =>

  case class Vec(x: Int, y: Int, z: Int):
    def apply(i: Int): Int = i match
      case 0 => x
      case 1 => y
      case 2 => z
    def +(that: Vec): Vec = Vec(x + that.x, y + that.y, z + that.z)
    def -(that: Vec): Vec = Vec(x - that.x, y - that.y, z - that.z)
    def abs: Int = x.abs + y.abs + z.abs
  object Vec:
    val arity: Int = 3
  val Zero = Vec(0, 0, 0)

  val points = input.split("\n\n").map(_.split("\n").tail.map {
    case s"$x,$y,$z" => Vec(x.toInt, y.toInt, z.toInt)
  })

  case class Permutation(permutation: Seq[(Int, Int)]):
    def apply(p: Vec): Vec =
      permutation.map { case (i, s) => p(i) * s } match
        case Seq(x, y, z) => Vec(x, y, z)
    def rotateX: Permutation = permutation match
      case Seq(xt, (y, sy), zt) => Permutation(Seq(xt, zt, (y, -sy)))
    def rotateY: Permutation = permutation match
      case Seq((x, sx), yt, zt) => Permutation(Seq(yt, (x, -sx), zt))
    def inverse: Permutation = Permutation(permutation.zipWithIndex.sortBy { case ((v, _), _) => v }.map { case ((_, s), i) => (i, s) })

  val permutations = View.iterate(Set(Permutation((0 until Vec.arity).map((_, 1)))), 6)(set => set ++ set.flatMap(p => Set(p.rotateX, p.rotateY))).last.toSeq

  case class Transformation(offset: Vec, permutation: Permutation):
    def apply(p: Vec): Vec = permutation(p) + offset
    def inverse: Transformation =
      val inversed = permutation.inverse
      Transformation(inversed(Zero - offset), inversed)

  val initialRemaining =
    for
      i <- points.indices.view
      j <- points.indices.view.drop(i + 1).view
    yield (i, j)

  val transformations = initialRemaining.foldLeft(Map.empty[(Int, Int), Transformation]) { case (map, (i, j)) =>
    val `12` = 12
    val (pointsI, pointsJ) = (points(i), points(j))
    val pointsISet = pointsI.toSet
    val result = permutations.view.flatMap { permutation =>
      val pointsJPermuted = pointsJ.map(permutation.apply)
      for
        vi <- pointsI.view.drop(`12` - 1)
        vj <- pointsJPermuted.view.drop(`12` - 1)
        relative = vi - vj
        if pointsJPermuted.count(p => pointsISet.contains(p + relative)) >= `12`
      yield Transformation(relative, permutation)
    }.headOption

    result.map(transform => map + ((j, i) -> transform) + ((i, j) -> transform.inverse)).getOrElse(map)
  }

  val graph: Map[Int, Set[(Int, Transformation)]] =
    transformations.groupBy { case ((u, _), _) => u }.view.mapValues(_.map { case ((_, v), t) => (v, t) }.toSet).toMap

  def bfs(toVisit: Set[Int], previous: Map[Int, Int]): Map[Int, Int] =
    if toVisit.nonEmpty then
      val next = toVisit.flatMap(i => graph(i).map((j, _) => j).filter(!previous.contains(_)).map(_ -> i)).toMap
      bfs(next.keySet, previous ++ next)
    else
      previous
  val zero = 0
  val paths = bfs(Set(zero), Map(zero -> zero))
  def reconstruct(last: Int, acc: Seq[Int] = Seq.empty): Seq[Int] =
    val newAcc = last +: acc
    paths.get(last) match
      case Some(previous) if previous != last => reconstruct(previous, newAcc)
      case _ => newAcc

  val finalTransformations = points.indices.map { i =>
    val path = reconstruct(i).reverse
    i -> path.zip(path.tail).map { case (u, v) => transformations((u, v)) }
  }.toMap

  val finalPoints = points.indices.flatMap(i => finalTransformations(i).foldLeft(points(i))((ps, t) => ps.map(t.apply)))

  part(1) = finalPoints.toSet.size

  val scanners = points.indices.map(i => finalTransformations(i).foldLeft(Zero)((p, t) => t(p)))
  val distances = initialRemaining.map((i, j) => (scanners(i) - scanners(j)).abs).max

  part(2) = distances

}
