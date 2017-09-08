package com.sword865.scalaJVM.rtda

/**
  * Created by tianhaowei on 2017/9/8.
  */

object Frame{
  def apply(maxLocals: Int, maxStack: Int): Frame = {
    new Frame(null, LocalVars(maxLocals), OperandStack(maxStack))
  }
}

class Frame(var lower: Frame, val localVars: LocalVars, val operandStack: OperandStack)
