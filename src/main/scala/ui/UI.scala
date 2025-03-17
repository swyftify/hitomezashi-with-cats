package ui

import scalafx.Includes.*
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.paint.Color.*
import scalafx.scene.shape.Rectangle

object UI {
  def apply(
      gridWidthAndHeight: (Int, Int),
      objectList: List[Rectangle]
  ): UI = {
    new UI(gridWidthAndHeight, objectList)
  }
}

case class UI(
    gridWidthAndHeight: (Int, Int),
    objectList: List[Rectangle]
) extends JFXApp3 {

  override def start(): Unit = {
    stage = new PrimaryStage {
      title.value = "Hitomezashi Pattern"
      width = gridWidthAndHeight._1
      height = gridWidthAndHeight._2
      scene = new Scene {
        fill = LightGrey
        content = objectList
      }
    }
  }
}
