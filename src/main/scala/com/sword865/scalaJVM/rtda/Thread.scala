package com.sword865.scalaJVM.rtda

/**
  * Created by tianhaowei on 2017/9/8.
  */

object Thread{
  def apply(): Thread = {
    new Thread(new Stack(1024))
  }
}

class Thread(stack: Stack, var pc: Int=0) {
  def getFrames: Array[Frame] = stack.getFrames


  def clearStack():Unit ={
    stack.clear()
  }


  def pushFrame(frame: Frame): Unit = {
    stack.push(frame)
  }

  def popFrame(): Frame = {
    stack.pop()
  }

  def topFrame(): Frame = {
    stack.top()
  }

  def currentFrame(): Frame = {
    stack.top()
  }

  def newFrame(maxLocals: Int, maxStack: Int): Frame = {
    new Frame(LocalVars(maxLocals), OperandStack(maxStack),this)
  }

  def newFrame(method: heap.Method): Frame = {
    Frame(this, method)
  }

  def isStackEmpty: Boolean ={
    stack.isEmpty
  }

}
