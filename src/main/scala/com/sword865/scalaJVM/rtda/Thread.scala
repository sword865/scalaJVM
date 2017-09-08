package com.sword865.scalaJVM.rtda

import scala.collection.mutable

/**
  * Created by tianhaowei on 2017/9/8.
  */

object Thread{
  def apply: Thread = {
    new Thread(new Stack(1024))
  }
}

class Thread(stack: Stack, var pc: Int=0) {

  def pushFrame(frame: Frame): Unit = {
    stack.push(frame)
  }

  def popFrame(): Frame = {
    stack.pop()
  }

  def currentFrame(): Frame = {
    stack.top()
  }

}
