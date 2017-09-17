package com.sword865.scalaJVM.rtda

/**
  * Created by tianhaowei on 2017/9/8.
  */

object Frame{

  def apply(thread: Thread, method: heap.Method): Frame = {
    new Frame(LocalVars(method.maxLocals), OperandStack(method.maxStack), thread, method)
  }

  def apply(maxLocals: Int, maxStack: Int): Frame = {
    new Frame(LocalVars(maxLocals), OperandStack(maxStack))
  }
}

class Frame(val localVars: LocalVars, val operandStack: OperandStack,
            var thread: Thread=null,var method: heap.Method = null,
            var nextPC: Int=0, var lower: Frame=null){

  def revertNextPC(): Unit ={
    nextPC = thread.pc
  }
}
