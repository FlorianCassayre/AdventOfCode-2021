package adventofcode

object Reflect:
  import scala.quoted.*
  import scala.compiletime._

  inline def generateAOCTests: Any = ${ generateAOCTestsImpl }

  private def generateAOCTestsImpl(using Quotes): Expr[Any] =
    import quotes.reflect.*
    val ignored = Set[Int]()
    val values: Seq[(String, (String, Term))] = (1 to 25).map { day =>
      val dayPadded = f"$day%02d"
      val method = Symbol.requiredMethod(s"adventofcode.solutions.Day$dayPadded")
      val testData = method.annotations.nonEmpty match { // Cheap trick to determine if the method is real or mocked
        case true if !ignored.contains(day) => ("test", Ref(method))
        case _ => ("ignore", '{ () }.asTerm)
      }
      (s"day $day", testData)
    }
    val testCasesTerm = values.foldLeft('{ () }.asTerm) { case (acc, (testName, (methodName, bodyTerm))) =>
      val argumentsAsTerms = List()
      val repeatedAnyTypeTree = Applied(TypeIdent(defn.RepeatedParamClass), List(TypeTree.of[org.scalatest.Tag]))
      val varargsTerm = Typed(Inlined(None, Nil, Repeated(argumentsAsTerms, TypeTree.of[Any])), repeatedAnyTypeTree)
      val test = Select.unique(resolveThis, methodName)
      val testTerm = Apply(Apply(test, List(Literal(StringConstant(testName)), varargsTerm)), List(bodyTerm))
      Block(List(acc), testTerm)
    }
    testCasesTerm.asExpr

  private def resolveThis(using Quotes): quotes.reflect.Term =
    import quotes.reflect.*
    var sym = Symbol.spliceOwner
    while sym != null && !sym.isClassDef do
      sym = sym.owner
    This(sym)
