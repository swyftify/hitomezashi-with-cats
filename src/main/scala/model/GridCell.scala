package model

import scalafx.scene.paint.Color

object GridCell {
  def apply(position: (Int, Int), color: Color): GridCell = {
    new GridCell(position = position, color = color)
  }
}

case class GridCell(position: (Int, Int), color: Color) {}
