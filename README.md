_My solutions to the 2021 edition of [Advent Of Code](https://adventofcode.com/2021)._

## Previous participations

* [2017](https://github.com/FlorianCassayre/AdventOfCode-2017)
* [2018](https://github.com/FlorianCassayre/AdventOfCode-2018)
* [2019](https://github.com/FlorianCassayre/AdventOfCode-2019)
* [2020](https://github.com/FlorianCassayre/AdventOfCode-2020)

## Problem statements & solutions

* [Day 01](https://adventofcode.com/2021/day/1): –[](https://github.com/FlorianCassayre/AdventOfCode-2021/blob/master/src/main/scala/adventofcode/solutions/Day01.scala)
* [Day 02](https://adventofcode.com/2021/day/2): –[](https://github.com/FlorianCassayre/AdventOfCode-2021/blob/master/src/main/scala/adventofcode/solutions/Day02.scala)
* [Day 03](https://adventofcode.com/2021/day/3): –[](https://github.com/FlorianCassayre/AdventOfCode-2021/blob/master/src/main/scala/adventofcode/solutions/Day03.scala)
* [Day 04](https://adventofcode.com/2021/day/4): –[](https://github.com/FlorianCassayre/AdventOfCode-2021/blob/master/src/main/scala/adventofcode/solutions/Day04.scala)
* [Day 05](https://adventofcode.com/2021/day/5): –[](https://github.com/FlorianCassayre/AdventOfCode-2021/blob/master/src/main/scala/adventofcode/solutions/Day05.scala)
* [Day 06](https://adventofcode.com/2021/day/6): –[](https://github.com/FlorianCassayre/AdventOfCode-2021/blob/master/src/main/scala/adventofcode/solutions/Day06.scala)
* [Day 07](https://adventofcode.com/2021/day/7): –[](https://github.com/FlorianCassayre/AdventOfCode-2021/blob/master/src/main/scala/adventofcode/solutions/Day07.scala)
* [Day 08](https://adventofcode.com/2021/day/8): –[](https://github.com/FlorianCassayre/AdventOfCode-2021/blob/master/src/main/scala/adventofcode/solutions/Day08.scala)
* [Day 09](https://adventofcode.com/2021/day/9): –[](https://github.com/FlorianCassayre/AdventOfCode-2021/blob/master/src/main/scala/adventofcode/solutions/Day09.scala)
* [Day 10](https://adventofcode.com/2021/day/10): –[](https://github.com/FlorianCassayre/AdventOfCode-2021/blob/master/src/main/scala/adventofcode/solutions/Day10.scala)
* [Day 11](https://adventofcode.com/2021/day/11): –[](https://github.com/FlorianCassayre/AdventOfCode-2021/blob/master/src/main/scala/adventofcode/solutions/Day11.scala)
* [Day 12](https://adventofcode.com/2021/day/12): –[](https://github.com/FlorianCassayre/AdventOfCode-2021/blob/master/src/main/scala/adventofcode/solutions/Day12.scala)
* [Day 13](https://adventofcode.com/2021/day/13): –[](https://github.com/FlorianCassayre/AdventOfCode-2021/blob/master/src/main/scala/adventofcode/solutions/Day13.scala)
* [Day 14](https://adventofcode.com/2021/day/14): –[](https://github.com/FlorianCassayre/AdventOfCode-2021/blob/master/src/main/scala/adventofcode/solutions/Day14.scala)
* [Day 15](https://adventofcode.com/2021/day/15): –[](https://github.com/FlorianCassayre/AdventOfCode-2021/blob/master/src/main/scala/adventofcode/solutions/Day15.scala)
* [Day 16](https://adventofcode.com/2021/day/16): –[](https://github.com/FlorianCassayre/AdventOfCode-2021/blob/master/src/main/scala/adventofcode/solutions/Day16.scala)
* [Day 17](https://adventofcode.com/2021/day/17): –[](https://github.com/FlorianCassayre/AdventOfCode-2021/blob/master/src/main/scala/adventofcode/solutions/Day17.scala)
* [Day 18](https://adventofcode.com/2021/day/18): –[](https://github.com/FlorianCassayre/AdventOfCode-2021/blob/master/src/main/scala/adventofcode/solutions/Day18.scala)
* [Day 19](https://adventofcode.com/2021/day/19): –[](https://github.com/FlorianCassayre/AdventOfCode-2021/blob/master/src/main/scala/adventofcode/solutions/Day19.scala)
* [Day 20](https://adventofcode.com/2021/day/20): –[](https://github.com/FlorianCassayre/AdventOfCode-2021/blob/master/src/main/scala/adventofcode/solutions/Day20.scala)
* [Day 21](https://adventofcode.com/2021/day/21): –[](https://github.com/FlorianCassayre/AdventOfCode-2021/blob/master/src/main/scala/adventofcode/solutions/Day21.scala)
* [Day 22](https://adventofcode.com/2021/day/22): –[](https://github.com/FlorianCassayre/AdventOfCode-2021/blob/master/src/main/scala/adventofcode/solutions/Day22.scala)
* [Day 23](https://adventofcode.com/2021/day/23): –[](https://github.com/FlorianCassayre/AdventOfCode-2021/blob/master/src/main/scala/adventofcode/solutions/Day23.scala)
* [Day 24](https://adventofcode.com/2021/day/24): –[](https://github.com/FlorianCassayre/AdventOfCode-2021/blob/master/src/main/scala/adventofcode/solutions/Day24.scala)
* [Day 25](https://adventofcode.com/2021/day/25): –[](https://github.com/FlorianCassayre/AdventOfCode-2021/blob/master/src/main/scala/adventofcode/solutions/Day25.scala)

## Guiding principles

In order to make the challenge more interesting, I fixed the following rules:

* **Pure**: no usage of `var` or mutable datastructures
* **Self-contained**: no third-party libraries, one file per day (*)
* **Efficient**: optimal asymptotic complexity, as far as reasonable
* **Concise**: readability is key

(*): this rule could be subject to modification, for instance if the puzzles implicitly require it ([Intcode](https://adventofcode.com/2019/day/9) in 2019).

## Usage

This project runs on [Scala](https://scala-lang.org) `3.0.2` and sbt `1.5.5`.

Use the following template to write a solution for a given day:

```Scala
package adventofcode.solutions

import adventofcode.Definitions.*

@main def Day01 = Day[1] { input ?=>

  part(1) = ???

  part(2) = ???

}
```
(change `1` to the current day number and fill in the `???`)

Paste your input as a file named `01.txt` in `input/`.

To run the code, enter `sbt run Day01`.

The output(s) will be printed to the console and stored in `output/` as `01-1.txt` and `01-2.txt`.

Additionally, the command `sbt test` will run all the implemented solutions and compare their result against the currently stored output.

## License

MIT
