package adventofcode.solutions

import adventofcode.Definitions.*
import scala.util.chaining.*

@main def Day02 = Day(2) { (input, part) =>

  enum Instruction:
    case Forward
    case Down
    case Up
  import Instruction._

  val instructions = input.toLines.map {
    case s"$instruction $x" => (
      instruction match
        case "forward" => Forward
        case "down" => Down
        case "up" => Up,
      x.toInt
    )
  }

  part(1) = instructions.foldLeft((0, 0)) { case ((horizontal, depth), (instruction, x)) =>
    instruction match
      case Forward => (horizontal + x, depth)
      case Down => (horizontal, depth + x)
      case Up => (horizontal, depth - x)
  }.pipe((horizontal, depth) => horizontal * depth)

  part(2) = instructions.foldLeft((0, 0, 0)) { case ((horizontal, depth, aim), (instruction, x)) =>
    instruction match
      case Down => (horizontal, depth, aim + x)
      case Up => (horizontal, depth, aim - x)
      case Forward => (horizontal + x, depth + aim * x, aim)
  }.pipe((horizontal, depth, _) => horizontal * depth)

}
