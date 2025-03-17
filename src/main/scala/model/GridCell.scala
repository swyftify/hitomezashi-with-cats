package model

import cats.effect.IO
import scalafx.scene.paint.Color
import scalafx.scene.paint.Color.Black
import scalafx.scene.shape.Rectangle

object GridCell {
  def apply(position: (Int, Int), color: Color): GridCell = {
    new GridCell(position = position, color = color)
  }
}

case class GridCell(position: (Int, Int), color: Color, cellSize: Int = 10) {

  def generateDrawObject(): IO[Rectangle] = {
    IO.pure(new Rectangle {
      x = position._1 * cellSize
      y = position._2 * cellSize
      width = cellSize
      height = cellSize
      fill = color
      stroke = Black
      strokeWidth = 1
    })
  }

}
