import cats.data.Kleisli
import cats.effect.*
import cats.syntax.all.*
import generator.{CoinTossGenerator, GridGenerator}
import model.GridCell
import scalafx.scene.shape.Rectangle
import ui.UI

object App extends IOApp.Simple {

  val width = 100
  val height = 100

  val grid = (width, height, CoinTossGenerator(): GridGenerator)

  private val cellGeneratorFunction
      : Kleisli[IO, (Int, Int, GridGenerator), Map[(Int, Int), GridCell]] =
    Kleisli { case (rows, cols, generator) =>
      (0 until rows).toList
        .flatMap(row =>
          (0 until cols).toList.map(col => generator.generate(row, col))
        )
        .parSequence
        .map(_.map(cell => cell.position -> cell).toMap)
    }

  private val objectGeneratorFunction
      : Kleisli[IO, Map[(Int, Int), GridCell], List[Rectangle]] =
    Kleisli { map => map.values.toList.parTraverse(_.generateDrawObject()) }

  val generatorFlow: Kleisli[IO, (Int, Int, GridGenerator), List[Rectangle]] =
    cellGeneratorFunction andThen objectGeneratorFunction

  override def run: IO[Unit] =
    generatorFlow.run(grid).flatMap { gridMap =>
      IO {
        println(gridMap)
        UI(width -> height, gridMap).main(Array.empty)
      }
    }
}
