package adventofcode

import java.io.{File, PrintWriter}
import scala.compiletime.ops.int.*
import scala.compiletime.constValue
import scala.io.Source
import scala.util.{Failure, Success, Try}

object Definitions:
  private[Definitions] var testMode = false

  type PartNumber = 1 | 2
  private type DayNumberHelper[N <: Int] <: Int = N match
    case 1 => N
    case _ => N | DayNumberHelper[N - 1]
  type DayNumber = DayNumberHelper[25]

  opaque type Input = String
  type Output = Any

  private inline def dayValue[N <: DayNumber]: N = constValue[N]

  trait Solution[N <: DayNumber]:
    def apply(input: Input, part: Part[N]): Unit

  val lineSeparator = "\n"
  extension (input: Input) def toLines: IndexedSeq[String] = input.split("\r?\n").toIndexedSeq

  final class Part[N <: DayNumber](day: N):
    import scala.collection.mutable
    private[Definitions] var last: Int = 0
    def update(part: PartNumber, v: => Output): Unit =
      require(part == last + 1)
      last = part
      Try(v).map(String.valueOf) match
        case Success(output) =>
          println(output)
          if !testMode then
            writeOutput(output, day, part)
          else
            val expected = Source.fromFile(pathForOutput(day, part)).mkString
            assert(output == expected, s"Day $day part $part: output '$output' didn't match '$expected'")
        case Failure(e: NotImplementedError) =>
          println(s"(ignored part $part)")
        case Failure(e) => throw new Exception(e)

  inline def Day[N <: DayNumber](day: N)(implementation: Solution[N]): Unit = implementation(readInput(day), new Part(constValue[N]))

  private def formatDay(day: DayNumber): String = f"$day%02d"

  private val (inputDirectory, outputDirectory) = ("input", "output")
  private def pathForInput(day: DayNumber): String =
    s"$inputDirectory/${formatDay(day)}.txt"
  private def pathForOutput(day: DayNumber, part: PartNumber): String =
    s"$outputDirectory/${formatDay(day)}-$part.txt"

  private def readInput(day: DayNumber): Input =
    Source.fromFile(pathForInput(day)).mkString

  private def writeOutput(output: String, day: DayNumber, part: PartNumber): Unit =
    val path = pathForOutput(day, part)
    new File(path).getParentFile.mkdirs()
    new PrintWriter(path):
      write(output)
      close()
