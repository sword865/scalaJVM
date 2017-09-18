package com.sword865.scalaJVM.instructions.references

import com.sword865.scalaJVM.instructions.base.Index16Instruction
import com.sword865.scalaJVM.rtda.{Frame, heap}

/**
  * Created by tianhaowei on 2017/9/18.
  */
class ANEW_ARRAY extends Index16Instruction{
  override def execute(frame: Frame): Unit = {
    val cp = frame.method.classStruct.constantPool
    val classRef = cp.getConstant(index).asInstanceOf[heap.ClassRef]
    val componentClass = classRef.resolvedClass()

    val stack = frame.operandStack
    val count = stack.popInt()
    if(count < 0){
      throw new Exception("java.lang.NegativeArraySizeException")
    }

    val arrClass = componentClass.arrayClass()
    val arr = arrClass.newArray(count)
    stack.pushRef(arr)
  }
}
