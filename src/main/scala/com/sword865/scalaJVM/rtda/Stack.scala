package com.sword865.scalaJVM.rtda

/**
  * Created by tianhaowei on 2017/9/8.
  */
class Stack(maxSize:Int,var size:Int = 0, var _top: Frame = null) {
  def push(frame: Frame): Unit ={
    if(size >= maxSize){
      throw new Exception("scalaJVM StackOverflow")
    }
    if(_top != null){
      frame.lower = _top
    }
    _top = frame
    size += 1
  }

  def pop(): Frame = {
    if(_top == null){
      throw new Exception("scalaJVM Stack is empty")
    }
    val top = _top
    _top = top.lower
    top.lower = null
    size -= 1

    top
  }

  def top(): Frame = {
    if(_top == null){
      throw new Exception("scalaJVM Stack is empty")
    }
    _top
  }
}
