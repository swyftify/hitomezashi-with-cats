import cats.data.Kleisli
import cats.effect.*
import cats.syntax.all.*
import generator.{CoinTossGenerator, GridGenerator}
import model.GridCell
import ui.UI

object App extends IOApp.Simple {

  val width = 100
  val height = 100

  val grid = (width, height, CoinTossGenerator(): GridGenerator)

  val cellGeneratorFunction
      : Kleisli[IO, (Int, Int, GridGenerator), Map[(Int, Int), GridCell]] =
    Kleisli { case (rows, cols, generator) =>
      (0 until rows).toList
        .flatMap(row =>
          (0 until cols).toList.map(col => generator.generate(row, col))
        )
        .parSequence
        .map(_.map(cell => cell.position -> cell).toMap)
    }

  override def run: IO[Unit] =
    cellGeneratorFunction.run(grid).flatMap { gridMap =>
      IO {
        println(gridMap)
        UI(width -> height, gridMap).main(Array.empty)
      }
    }
}
