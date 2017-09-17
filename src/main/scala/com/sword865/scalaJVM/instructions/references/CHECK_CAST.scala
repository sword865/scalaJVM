package com.sword865.scalaJVM.instructions.references

import com.sword865.scalaJVM.instructions.base.Index16Instruction
import com.sword865.scalaJVM.rtda.{Frame, heap}

class CHECK_CAST extends Index16Instruction{
  override def execute(frame: Frame): Unit = {
    val stack = frame.operandStack
    val ref = stack.popRef()
    stack.pushRef(ref)
    if (ref != null) {
      val cp = frame.method.classStruct.constantPool
      val classRef = cp.getConstant(index).asInstanceOf[heap.ClassRef]
      val classStruct = classRef.resolvedClass()
      if (!ref.isInstanceOf(classStruct)) {
        throw new Exception("java.lang.ClassCastException")
      }
    }
  }
}
