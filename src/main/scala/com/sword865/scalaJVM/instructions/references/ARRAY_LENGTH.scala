package com.sword865.scalaJVM.instructions.references

import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction
import com.sword865.scalaJVM.rtda.Frame

/**
  * Created by tianhaowei on 2017/9/18.
  */
class ARRAY_LENGTH extends NoOperandsInstruction{
  override def execute(frame: Frame): Unit = {
    val stack = frame.operandStack
    val arrRef = stack.popRef()
    if(arrRef == null){
      throw new Exception("java.lang.NullPointerException")
    }
    val arrLen = arrRef.arrayLength
    stack.pushInt(arrLen)
  }
}
