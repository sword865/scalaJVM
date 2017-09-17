package com.sword865.scalaJVM.instructions.references

import com.sword865.scalaJVM.instructions.base.Index16Instruction
import com.sword865.scalaJVM.rtda.{Frame, heap}

class NEW extends Index16Instruction{
  override def execute(frame: Frame): Unit = {
    val cp = frame.method.classStruct.constantPool
    val classRef = cp.getConstant(index).asInstanceOf[heap.ClassRef]
    val classStruct = classRef.resolvedClass()
    //TODO: init class
    if(classStruct.isInterface || classStruct.isAbstract){
      throw new Exception("java.lang.InstantiationError")
    }
    val ref = classStruct.newObject()
    frame.operandStack.pushRef(ref)
  }
}
