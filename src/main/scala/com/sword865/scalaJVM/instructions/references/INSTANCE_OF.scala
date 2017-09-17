package com.sword865.scalaJVM.instructions.references

import com.sword865.scalaJVM.instructions.base.Index16Instruction
import com.sword865.scalaJVM.rtda.{Frame, heap}

class INSTANCE_OF extends Index16Instruction{
  override def execute(frame: Frame): Unit = {
    val stack = frame.operandStack
    val ref = stack.popRef()
    if(ref == null){
      stack.pushInt(0)
    }else{
      val cp = frame.method.classStruct.constantPool
      val classRef = cp.getConstant(index).asInstanceOf[heap.ClassRef]
      val classStruct = classRef.resolvedClass()
      if(ref.isInstanceOf(classStruct)){
        stack.pushInt(1)
      }else{
        stack.pushInt(0)
      }
    }

  }
}
