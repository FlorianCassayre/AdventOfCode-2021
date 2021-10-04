package adventofcode

import java.io.{File, PrintWriter}
import scala.compiletime.ops.int.*
import scala.io.Source
import scala.util.{Failure, Success, Try}

object Definitions:
  private[Definitions] var testMode = false

  type PartNumber = 1 | 2
  type DayNumberWitness[N <: Int] = ((1 <= N) & (N <= 25)) =:= true

  opaque type Input[N <: Int] <: String = String
  type Output = Any

  trait Solution[N <: Int]:
    def apply(using input: Input[N]): Unit

  val lineSeparator = "\n"
  extension [N <: Int](input: Input[N]) def toLines: IndexedSeq[String] = input.split("\r?\n").toIndexedSeq

  final class Part private[Definitions]():
    import scala.collection.mutable
    private[Definitions] var last: Int = 0
    def update[N <: Int](part: PartNumber, v: => Output)(using input: Input[N], day: ValueOf[N], w: DayNumberWitness[N]): Unit =
      require(part == last + 1)
      last = part
      Try(v).map(String.valueOf) match
        case Success(output) =>
          println(output)
          if !testMode then
            writeOutput(output, day.value, part)
          else
            val expected = Source.fromFile(pathForOutput(day.value, part)).mkString
            assert(output == expected, s"Day ${day.value} part $part: output '$output' didn't match '$expected'")
        case Failure(e: NotImplementedError) =>
          println(s"(ignored part $part)")
        case Failure(e) => throw new Exception(e)

  val part = new Part()

  def Day[N <: Int](implementation: Solution[N])(using day: ValueOf[N], w: DayNumberWitness[N]): Unit =
    part.last = 0
    implementation(using readInput(day.value))

  private def formatDay[N <: Int](day: N)(using w: DayNumberWitness[N]): String = f"$day%02d"

  private val (inputDirectory, outputDirectory) = ("input", "output")
  private def pathForInput[N <: Int](day: N)(using w: DayNumberWitness[N]): String =
    s"$inputDirectory/${formatDay(day)}.txt"
  private def pathForOutput[N <: Int](day: N, part: PartNumber)(using w: DayNumberWitness[N]): String =
    s"$outputDirectory/${formatDay(day)}-$part.txt"

  private def readInput[N <: Int](day: N)(using w: DayNumberWitness[N]): Input[N] =
    Source.fromFile(pathForInput(day)).mkString

  private def writeOutput[N <: Int](output: String, day: N, part: PartNumber)(using w: DayNumberWitness[N]): Unit =
    val path = pathForOutput(day, part)
    new File(path).getParentFile.mkdirs()
    new PrintWriter(path):
      write(output)
      close()
