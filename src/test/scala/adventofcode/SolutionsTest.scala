package adventofcode

import adventofcode.Definitions.*
import adventofcode.Reflect.generateAOCTests
import org.scalatest.funsuite.AnyFunSuite

import collection.JavaConverters.*
import java.io.File
import java.net.URL
import scala.io.Source

class SolutionsTest extends AnyFunSuite {

  val testModeField = Definitions.getClass.getDeclaredField("testMode")
  testModeField.setAccessible(true)
  testModeField.set(Definitions, true)

  generateAOCTests

}
