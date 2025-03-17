package generator

import cats.effect.IO
import cats.effect.std.Random
import model.GridCell
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.{Black, Blue, Green, Purple, Red, White, Yellow}



sealed trait GridGenerator {
  def generate(position: (Int, Int)): IO[GridCell]
}

object CoinTossGenerator {
  def apply(): CoinTossGenerator = {
    new CoinTossGenerator
  }
}

case class CoinTossGenerator() extends GridGenerator {
  override def generate(position: (Int, Int)): IO[GridCell] = {
    for {
      random <- Random.scalaUtilRandom[IO]
      value <- random.betweenInt(0, 2)
    } yield value match
        case 0 => GridCell(position, White)
        case 1 => GridCell(position, Red)
  }
}

case class DiceGenerator() extends GridGenerator {

  val colorMap: Map[Int, Color] = Map(1 -> Red, 2 -> Blue, 3 -> Green, 4 -> Purple, 5 -> Black, 6 -> Yellow)

  override def generate(position: (Int, Int)): IO[GridCell] = {
    for {
      random <- Random.scalaUtilRandom[IO]
      value <- random.betweenInt(0, 6).map(_ + 1)
    } yield GridCell(position = position, color = colorMap(value))
  }


}
