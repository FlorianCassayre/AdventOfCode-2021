package adventofcode.solutions

import adventofcode.Definitions.*

import scala.collection.View
import scala.util.chaining.*

@main def Day23 = Day(23) { (input, part) =>

  val symbols = 'A' to 'D'
  val costs = symbols.zip(Seq.iterate(1, symbols.size)(_ * 10)).toMap

  val stacksCount = symbols.size
  val initialStacks = input.collect { case c if c.isLetter => c }.grouped(stacksCount).toIndexedSeq.map(_.toIndexedSeq).transpose

  case class State(stackCapacity: Int,
                   stacks: IndexedSeq[Seq[Char]],
                   left: IndexedSeq[Option[Char]],
                   right: IndexedSeq[Option[Char]],
                   interleaving: IndexedSeq[Option[Char]],
                   cost: Int)

  val sideCapacity = 2
  val initialSide = IndexedSeq.fill(sideCapacity)(None)
  val initialState = State(initialStacks.head.size, initialStacks, initialSide, initialSide, initialStacks.tail.map(_ => None), 0)

  def isComplete(s: State): Boolean = symbols.zip(s.stacks).forall { case (c, stack) => stack.size == s.stackCapacity && stack.forall(_ == c) }

  def next(s: State): Seq[State] =
    def distanceVerticalLeaving(bucketId: Int): Int = s.stackCapacity - s.stacks(bucketId).size + 1
    def distanceVerticalEntering(bucketId: Int): Int = s.stackCapacity - s.stacks(bucketId).size
    def distanceHorizontalInterleaving(bucketId: Int, interleavingId: Int): Int =
      if bucketId <= interleavingId then 2 * (interleavingId - bucketId + 1) - 1 else 2 * (bucketId - interleavingId) - 1
    def distanceHorizontalLeft(left: Int, bucketId: Int): Int = (sideCapacity - left) + 2 * bucketId
    def distanceHorizontalRight(right: Int, bucketId: Int): Int = (right + 1) + 2 * (stacksCount - 1 - bucketId)

    val from =
      for
        stackId <- s.stacks.indices.filter(bucketId => s.stacks(bucketId).nonEmpty).filter(bucketId => s.stacks(bucketId).exists(_ != symbols(bucketId)))
        symbol = s.stacks(stackId).head
        multiplier = costs(symbol)
        newStacks = s.stacks.updated(stackId, s.stacks(stackId).tail)
        distanceVertical = distanceVerticalLeaving(stackId)
        toInterleaving =
          for
            interleavingId <- s.interleaving.indices
            interleavingRange = if interleavingId >= stackId then stackId to interleavingId else interleavingId until stackId
            if interleavingRange.forall(i => s.interleaving(i).isEmpty)
            cost = (distanceVertical + distanceHorizontalInterleaving(stackId, interleavingId)) * multiplier
          yield s.copy(stacks = newStacks, interleaving = s.interleaving.updated(interleavingId, Some(symbol)), cost = s.cost + cost)
        toLeft =
          for
            slotId <- 0 until sideCapacity
            if (slotId until sideCapacity).forall(i => s.left(i).isEmpty)
            if (0 until stackId).forall(i => s.interleaving(i).isEmpty)
            cost = (distanceVertical + distanceHorizontalLeft(slotId, stackId)) * multiplier
          yield s.copy(stacks = newStacks, left = s.left.updated(slotId, Some(symbol)), cost = s.cost + cost)
        toRight =
          for
            slotId <- 0 until sideCapacity
            if (0 to slotId).forall(i => s.right(i).isEmpty)
            if (stackId until stacksCount - 1).forall(i => s.interleaving(i).isEmpty)
            cost = (distanceVertical + distanceHorizontalRight(slotId, stackId)) * multiplier
          yield s.copy(stacks = newStacks, right = s.right.updated(slotId, Some(symbol)), cost = s.cost + cost)
      yield Seq(toInterleaving, toLeft, toRight).flatten

    val to =
      for
        stackId <- s.stacks.indices.filter(i => s.stacks(i).sizeIs < s.stackCapacity).filter(i => s.stacks(i).forall(_ == symbols(i)))
        expectedValue = symbols(stackId)
        multiplier = costs(expectedValue)
        newStacks = s.stacks.updated(stackId, expectedValue +: s.stacks(stackId))
        distanceVertical = distanceVerticalEntering(stackId)
        interleavingTo =
          for
            interleavingId <- s.interleaving.indices
            value <- s.interleaving(interleavingId)
            if value == expectedValue
            range = if (stackId <= interleavingId) stackId until interleavingId else (interleavingId + 1) until stackId
            if range.forall(i => s.interleaving(i).isEmpty)
            cost = (distanceVertical + distanceHorizontalInterleaving(stackId, interleavingId)) * multiplier
          yield s.copy(stacks = newStacks, interleaving = s.interleaving.updated(interleavingId, None), cost = s.cost + cost)
        leftTo =
          for
            slotId <- s.left.indices.findLast(i => s.left(i).nonEmpty)
            value = s.left(slotId).get
            if value == expectedValue
            range = 0 until stackId
            if range.forall(i => s.interleaving(i).isEmpty)
            cost = (distanceVertical + distanceHorizontalLeft(slotId, stackId)) * multiplier
          yield s.copy(stacks = newStacks, left = s.left.updated(slotId, None), cost = s.cost + cost)
        rightTo =
          for
            slotId <- s.right.indices.find(i => s.right(i).nonEmpty)
            value = s.right(slotId).get
            if value == expectedValue
            range = stackId until s.interleaving.size
            if range.forall(i => s.interleaving(i).isEmpty)
            cost = (distanceVertical + distanceHorizontalRight(slotId, stackId)) * multiplier
          yield s.copy(stacks = newStacks, right = s.right.updated(slotId, None), cost = s.cost + cost)
      yield Seq(interleavingTo, leftTo, rightTo).flatten

    from.flatten ++ to.flatten

  def bfs(states: Set[State], minOption: Option[Int]): Option[Int] =
    if states.isEmpty then
      minOption
    else
      val (complete, incomplete) = states.partition(isComplete)
      val newMin = (complete.map(_.cost) ++ minOption.toSet).minOption
      val nextStates = incomplete.flatMap(next).filter(s => newMin.forall(s.cost < _)).groupBy(state => state.copy(cost = 0)).values.map(_.minBy(_.cost)).toSet
      bfs(nextStates, newMin)

  def solve(initialState: State): Int = bfs(Set(initialState), None).get

  part(1) = solve(initialState)

  val initial2 = initialStacks.transpose.pipe(initialT => initialT.head +: "DCBA".toIndexedSeq +: "DBAC".toIndexedSeq +: initialT.tail).pipe(_.transpose)
  val initialState2 = initialState.copy(stackCapacity = initial2.head.size, stacks = initial2)

  part(2) = solve(initialState2)

}
