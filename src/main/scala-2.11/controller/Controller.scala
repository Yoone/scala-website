package controller

import annotation.Route

/**
 * Created by yoone on 08/07/15.
 */
object Controller {
  @Route("/")
  def index (): Unit = {
    println("Index page")
  }

  @Route("/upload")
  def upload (): Unit = {
    println("Upload page")
  }
}
