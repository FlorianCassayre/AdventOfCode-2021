_My solutions to the 2021 edition of [Advent of Code](https://adventofcode.com/2021)._

## Previous participations

* [2020](https://github.com/FlorianCassayre/AdventOfCode-2020)
* [2019](https://github.com/FlorianCassayre/AdventOfCode-2019)
* [2018](https://github.com/FlorianCassayre/AdventOfCode-2018)
* [2017](https://github.com/FlorianCassayre/AdventOfCode-2017)

## Problem statements & solutions

<div align="center">

  | Day | Code | Part 1 | Part 2 |
  |:---:|:---:|:---|:---|
  | **[01](https://adventofcode.com/2021/day/1)** | [](src/main/scala/adventofcode/solutions/Day01.scala) |  |  |
  | **[02](https://adventofcode.com/2021/day/2)** | [](src/main/scala/adventofcode/solutions/Day02.scala) |  |  |
  | **[03](https://adventofcode.com/2021/day/3)** | [](src/main/scala/adventofcode/solutions/Day03.scala) |  |  |
  | **[04](https://adventofcode.com/2021/day/4)** | [](src/main/scala/adventofcode/solutions/Day04.scala) |  |  |
  | **[05](https://adventofcode.com/2021/day/5)** | [](src/main/scala/adventofcode/solutions/Day05.scala) |  |  |
  | **[06](https://adventofcode.com/2021/day/6)** | [](src/main/scala/adventofcode/solutions/Day06.scala) |  |  |
  | **[07](https://adventofcode.com/2021/day/7)** | [](src/main/scala/adventofcode/solutions/Day07.scala) |  |  |
  | **[08](https://adventofcode.com/2021/day/8)** | [](src/main/scala/adventofcode/solutions/Day08.scala) |  |  |
  | **[09](https://adventofcode.com/2021/day/9)** | [](src/main/scala/adventofcode/solutions/Day09.scala) |  |  |
  | **[10](https://adventofcode.com/2021/day/10)** | [](src/main/scala/adventofcode/solutions/Day10.scala) |  |  |
  | **[11](https://adventofcode.com/2021/day/11)** | [](src/main/scala/adventofcode/solutions/Day11.scala) |  |  |
  | **[12](https://adventofcode.com/2021/day/12)** | [](src/main/scala/adventofcode/solutions/Day12.scala) |  |  |
  | **[13](https://adventofcode.com/2021/day/13)** | [](src/main/scala/adventofcode/solutions/Day13.scala) |  |  |
  | **[14](https://adventofcode.com/2021/day/14)** | [](src/main/scala/adventofcode/solutions/Day14.scala) |  |  |
  | **[15](https://adventofcode.com/2021/day/15)** | [](src/main/scala/adventofcode/solutions/Day15.scala) |  |  |
  | **[16](https://adventofcode.com/2021/day/16)** | [](src/main/scala/adventofcode/solutions/Day16.scala) |  |  |
  | **[17](https://adventofcode.com/2021/day/17)** | [](src/main/scala/adventofcode/solutions/Day17.scala) |  |  |
  | **[18](https://adventofcode.com/2021/day/18)** | [](src/main/scala/adventofcode/solutions/Day18.scala) |  |  |
  | **[19](https://adventofcode.com/2021/day/19)** | [](src/main/scala/adventofcode/solutions/Day19.scala) |  |  |
  | **[20](https://adventofcode.com/2021/day/20)** | [](src/main/scala/adventofcode/solutions/Day20.scala) |  |  |
  | **[21](https://adventofcode.com/2021/day/21)** | [](src/main/scala/adventofcode/solutions/Day21.scala) |  |  |
  | **[22](https://adventofcode.com/2021/day/22)** | [](src/main/scala/adventofcode/solutions/Day22.scala) |  |  |
  | **[23](https://adventofcode.com/2021/day/23)** | [](src/main/scala/adventofcode/solutions/Day23.scala) |  |  |
  | **[24](https://adventofcode.com/2021/day/24)** | [](src/main/scala/adventofcode/solutions/Day24.scala) |  |  |
  | **[25](https://adventofcode.com/2021/day/25)** | [](src/main/scala/adventofcode/solutions/Day25.scala) |  |  |

</div>

The third and last row indicate the time and rank I obtained for that part.

In order to make the challenge more interesting, I set myself the following rules:

* **Pure**: no usage of `var` or mutable datastructures
* **Self-contained**: no third-party libraries, one file per day (*)
* **Efficient**: optimal asymptotic complexity, as far as reasonable
* **Concise**: readability is key

Note that these rules do not necessarily apply while _solving_ a problem, but rather when _committing_ the code to this repository.

(*): this rule could be subject to modification, for instance if the puzzles implicitly require it ([Intcode](https://adventofcode.com/2019/day/9) in 2019).

## Usage

This project runs on [Scala](https://scala-lang.org) `3.0.2` and sbt `1.5.5`.

Use the following template to write a solution for a given day:

```Scala
package adventofcode.solutions

import adventofcode.Definitions.*

@main def Day01 = Day(1) { input ?=>

  part(1) = ???

  part(2) = ???

}
```
(change `1` to the current day number and fill in the `???`)

Paste your input as a file named `01.txt` in `input/`.

To run the code, enter `sbt run Day01`.

The output(s) will be printed to the console and stored in `output/` as `01-1.txt` and `01-2.txt`.

Additionally, the command `sbt test` will run all the implemented solutions and compare their result against the currently stored output, to detect any potential regression.

## License

MIT
