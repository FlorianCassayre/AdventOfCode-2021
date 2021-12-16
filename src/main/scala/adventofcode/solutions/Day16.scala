package adventofcode.solutions

import adventofcode.Definitions.*
import scala.util.chaining.*

@main def Day16 = Day(16) { (input, part) =>

  enum Packet(version: Long):
    case Literal(version: Long, value: Long) extends Packet(version)
    case Operator(version: Long, typeId: Long, children: Seq[Packet]) extends Packet(version)
  import Packet._

  def hexToBits(v: Int, acc: Seq[Int]): Seq[Int] = if acc.sizeIs < 4 then hexToBits(v >> 1, (v % 2) +: acc) else acc
  def fromBits(bits: Seq[Int]): Long = java.lang.Long.parseLong(bits.mkString, 2)

  val bits = input.flatMap(c => hexToBits(Integer.parseInt(c.toString, 16), Seq.empty))

  def decode(bits: Seq[Int]): (Packet, Seq[Int]) =
    bits.splitAt(3).pipe { (versionSeq, bits) =>
      val version = fromBits(versionSeq)
      bits.splitAt(3).pipe { (typeIdSeq, bits) =>
        val typeId = fromBits(typeIdSeq)
        if typeId == 4 then
          def readValue(bits: Seq[Int], acc: Seq[Int]): (Seq[Int], Seq[Int]) =
            bits.splitAt(5).pipe { case (h +: tail, bits) =>
              val data = acc ++ tail
              if h == 1 then readValue(bits, data) else (data, bits)
            }
          readValue(bits, Seq.empty).pipe((valueSeq, bits) => (Literal(version, fromBits(valueSeq)), bits))
        else
          bits match
            case 0 +: bits =>
              bits.splitAt(15).pipe { (totalLengthSeq, bits) =>
                val totalLength = fromBits(totalLengthSeq).toInt
                bits.splitAt(totalLength).pipe { (children, bits) =>
                  def readAll(bits: Seq[Int], acc: Seq[Packet]): Seq[Packet] =
                    if bits.nonEmpty then decode(bits).pipe((packet, bits) => readAll(bits, acc :+ packet)) else acc
                  (Operator(version, typeId, readAll(children, Seq.empty)), bits)
                }
              }
            case 1 +: bits =>
              bits.splitAt(11).pipe { (numberSubPacketsSeq, bits) =>
                val numberSubPackets = fromBits(numberSubPacketsSeq).toInt
                def readChildren(bits: Seq[Int], acc: Seq[Packet]): (Seq[Packet], Seq[Int]) =
                  if acc.sizeIs < numberSubPackets then decode(bits).pipe((child, bits) => readChildren(bits, acc :+ child)) else (acc, bits)
                readChildren(bits, Seq.empty).pipe((children, bits) => (Operator(version, typeId, children), bits))
              }
      }
    }

  val (packet, _) = decode(bits)

  def versionSum(packet: Packet): Long = packet match
    case Literal(version, _) => version
    case Operator(version, _, children) => version + children.map(versionSum).sum

  part(1) = versionSum(packet)

  def evaluate(packet: Packet): Long = packet match
    case Literal(_, value) => value
    case Operator(_, typeId, children) =>
      val operands = children.map(evaluate)
      typeId match
        case 0 => operands.sum
        case 1 => operands.product
        case 2 => operands.min
        case 3 => operands.max
        case 5 => if operands.head > operands.tail.head then 1 else 0
        case 6 => if operands.head < operands.tail.head then 1 else 0
        case 7 => if operands.head == operands.tail.head then 1 else 0

  part(2) = evaluate(packet)

}
