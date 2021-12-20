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
  | **[01](https://adventofcode.com/2021/day/1)** | [solution](src/main/scala/adventofcode/solutions/Day01.scala) | `00:01:33 / 319` | `00:14:03 / 3652` |
  | **[02](https://adventofcode.com/2021/day/2)** | [solution](src/main/scala/adventofcode/solutions/Day02.scala) | `00:02:18 / 414` | `00:05:03 / 899` |
  | **[03](https://adventofcode.com/2021/day/3)** | [solution](src/main/scala/adventofcode/solutions/Day03.scala) | `00:12:08 / 3949` | `00:36:11 / 3416` |
  | **[04](https://adventofcode.com/2021/day/4)** | [solution](src/main/scala/adventofcode/solutions/Day04.scala) | `00:15:35 / 678` | `00:22:21 / 807` |
  | **[05](https://adventofcode.com/2021/day/5)** | [solution](src/main/scala/adventofcode/solutions/Day05.scala) |  |  |
  | **[06](https://adventofcode.com/2021/day/6)** | [solution](src/main/scala/adventofcode/solutions/Day06.scala) | `00:04:39 / 486` | `00:10:34 / 535` |
  | **[07](https://adventofcode.com/2021/day/7)** | [solution](src/main/scala/adventofcode/solutions/Day07.scala) | `00:04:27 / 1117` | `00:06:07 / 557` |
  | **[08](https://adventofcode.com/2021/day/8)** | [solution](src/main/scala/adventofcode/solutions/Day08.scala) | `00:13:22 / 3326` | `00:21:17 / 106` |
  | **[09](https://adventofcode.com/2021/day/9)** | [solution](src/main/scala/adventofcode/solutions/Day09.scala) | `00:05:45 / 456` | `00:19:51 / 716` |
  | **[10](https://adventofcode.com/2021/day/10)** | [solution](src/main/scala/adventofcode/solutions/Day10.scala) | `00:07:37 / 826` | `00:26:13 / 2916` |
  | **[11](https://adventofcode.com/2021/day/11)** | [solution](src/main/scala/adventofcode/solutions/Day11.scala) | `00:21:48 / 1483` | `00:23:34 / 1315` |
  | **[12](https://adventofcode.com/2021/day/12)** | [solution](src/main/scala/adventofcode/solutions/Day12.scala) | `00:23:24 / 1959` | `00:36:16 / 1698` |
  | **[13](https://adventofcode.com/2021/day/13)** | [solution](src/main/scala/adventofcode/solutions/Day13.scala) | `00:14:32 / 950` | `00:15:15 / 410` |
  | **[14](https://adventofcode.com/2021/day/14)** | [solution](src/main/scala/adventofcode/solutions/Day14.scala) | `00:11:07 / 1055` | `01:02:12 / 2966` |
  | **[15](https://adventofcode.com/2021/day/15)** | [solution](src/main/scala/adventofcode/solutions/Day15.scala) | `00:51:52 / 3469` | `01:19:39 / 2672` |
  | **[16](https://adventofcode.com/2021/day/16)** | [solution](src/main/scala/adventofcode/solutions/Day16.scala) | `00:24:39 / 171` | `00:44:47 / 576` |
  | **[17](https://adventofcode.com/2021/day/17)** | [solution](src/main/scala/adventofcode/solutions/Day17.scala) | `00:12:12 / 321` | `00:19:30 / 401` |
  | **[18](https://adventofcode.com/2021/day/18)** | [solution](src/main/scala/adventofcode/solutions/Day18.scala) | `01:50:25 / 1389` | `01:52:16 / 1259` |
  | **[19](https://adventofcode.com/2021/day/19)** | [solution](src/main/scala/adventofcode/solutions/Day19.scala) | `03:20:15 / 1516` | `03:32:21 / 1432` |
  | **[20](https://adventofcode.com/2021/day/20)** | [solution](src/main/scala/adventofcode/solutions/Day20.scala) | `00:16:42 / 107` | `01:12:12 / 2295` |
  | **[21](https://adventofcode.com/2021/day/21)** | [](src/main/scala/adventofcode/solutions/Day21.scala) |  |  |
  | **[22](https://adventofcode.com/2021/day/22)** | [](src/main/scala/adventofcode/solutions/Day22.scala) |  |  |
  | **[23](https://adventofcode.com/2021/day/23)** | [](src/main/scala/adventofcode/solutions/Day23.scala) |  |  |
  | **[24](https://adventofcode.com/2021/day/24)** | [](src/main/scala/adventofcode/solutions/Day24.scala) |  |  |
  | **[25](https://adventofcode.com/2021/day/25)** | [](src/main/scala/adventofcode/solutions/Day25.scala) |  |  |

</div>

The third and last row indicate the time and rank I obtained for that part. Empty cells mean no participation.

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

@main def Day01 = Day(1) { (input, part) =>

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
