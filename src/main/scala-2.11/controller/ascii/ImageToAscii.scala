package controller.ascii

import java.io._
import javax.imageio._

/**
 * Created by mecavity on 11/07/15.
 */
object ImageToAscii {
  private def getAscii(rgb: Array[Int], scale: Int): Char = {
    var avr = 0.0
    for (i <- rgb.indices) {
      val red = (rgb(i) >> 16) & 0xFF
      val green = (rgb(i) >> 8) & 0xFF
      val blue = rgb(i) & 0xFF
      avr += red * 0.299 + green * 0.587 + blue * 0.114
    }
    avr /= scale * scale
    avr match {
      case x if x < 60 => '='
      case x if x < 120 => '&'
      case x if x < 180 => '-'
      case x if x < 210 => '0'
      case _ => '.'
    }
  }

  def imgToAscii(name: String): String = {
    val img = ImageIO.read(new File(name))
    val wanted_width = 300
    val scale = 10

    var buff: String = ""

    for (y <- 0 until img.getHeight - 1 by scale) {
      for (x <- 0 until img.getWidth - 1 by scale) {
      if (y + scale < img.getHeight && x + scale < img.getWidth)
        buff += getAscii(img.getRGB(x, y, scale, scale, null, 0, scale), scale)
      }
      buff += "\n"
    }
    buff
  }
}
