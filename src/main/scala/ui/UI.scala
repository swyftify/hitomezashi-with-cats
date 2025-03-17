package ui

import model.GridCell
import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.paint.Color.*
import scalafx.scene.shape.Rectangle

object UI {
  def apply(
      gridWidthAndHeight: (Int, Int),
      gridMap: Map[(Int, Int), GridCell]
  ): UI = {
    new UI(gridWidthAndHeight, gridMap)
  }
}

case class UI(
    gridWidthAndHeight: (Int, Int),
    gridMap: Map[(Int, Int), GridCell]
) extends JFXApp3 {

  private val cellSize = 10 //pixels

  override def start(): Unit = {
    stage = new PrimaryStage {
      title.value = "Hitomezashi Pattern"
      width = gridWidthAndHeight._1
      height = gridWidthAndHeight._2
      scene = new Scene {
        fill = LightGrey
        content = gridMap.collect {

          case (position, GridCell((xPos, yPos), color)) =>
            new Rectangle {
              x = xPos * cellSize
              y = yPos * cellSize
              width = cellSize
              height = cellSize
              fill = color
              stroke = Black
              strokeWidth = 1
            }
        }
      }
    }
  }
}
