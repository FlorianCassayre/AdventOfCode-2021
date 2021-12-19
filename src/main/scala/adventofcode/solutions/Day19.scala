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
  }.toVector).toVector

  case class Permutation(permutation: Seq[(Int, Int)]):
    def apply(p: Vec): Vec =
      permutation.map { case (i, s) => p(i) * s } match
        case Seq(x, y, z) => Vec(x, y, z)
    def inverse: Permutation = Permutation(permutation.zipWithIndex.sortBy { case ((v, _), _) => v }.map { case ((_, s), i) => (i, s) })

  val permutations =
    Seq.fill(Vec.arity)(Seq(-1, 1)).flatten.combinations(Vec.arity).flatMap(_.permutations)
      .flatMap(signs => (0 until Vec.arity).permutations.map(permutation => Permutation(permutation.zip(signs)))).toIndexedSeq

  case class Transformation(offset: Vec, permutation: Permutation):
    def apply(p: Vec): Vec = permutation(p) + offset
    def inverse: Transformation =
      val inversed = permutation.inverse
      Transformation(inversed(Zero - offset), inversed)

  def search(remaining: View[(Int, Int)], representation: Map[(Int, Int), Transformation]): Map[(Int, Int), Transformation] =
    remaining.headOption match
      case Some((i, j)) =>
        val (pointsI, pointsJ) = (points(i), points(j))
        val result = permutations.indices.view.flatMap { k =>
          val permutation = permutations(k)
          val pointsJPermuted = pointsJ.map(permutation.apply)
          val exhaustive =
            for
              vi <- pointsI.indices.view
              vj <- pointsJPermuted.indices.view
            yield (vi, vj)
          exhaustive.flatMap { case (vi, vj) =>
            val relative = pointsI(vi) - pointsJPermuted(vj)
            val transformedJ = pointsJPermuted.map(_ + relative)
            if pointsI.toSet.intersect(transformedJ.toSet).sizeIs >= 12 then
              Some(Transformation(relative, permutation))
            else
              None
          }
        }.headOption

        val newRepresentation = result match
          case Some(transform) =>
            representation +
              ((j, i) -> transform) +
              ((i, j) -> transform.inverse)
          case _ => representation
        search(remaining.tail, newRepresentation)
      case _ => representation

  val initialRemaining =
    for
      i <- points.indices.view
      j <- points.indices.view.drop(i + 1).view
    yield (i, j)

  val transformations = search(initialRemaining, Map.empty)

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

  val scanners = points.indices.map(i => finalTransformations(i).foldLeft(Zero)((p, t) => t(p)))

  val distances = initialRemaining.map((i, j) => (scanners(i) - scanners(j)).abs).max

  part(1) = finalPoints.toSet.size

  part(2) = distances

}
