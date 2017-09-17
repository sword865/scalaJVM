package com.sword865.scalaJVM.instructions.references

import com.sword865.scalaJVM.instructions.base
import com.sword865.scalaJVM.instructions.base.Index16Instruction
import com.sword865.scalaJVM.rtda
import com.sword865.scalaJVM.rtda.heap

class INVOKE_STATIC extends Index16Instruction{
  override def execute(frame: rtda.Frame): Unit = {
    val cp = frame.method.classStruct.constantPool
    val methodRef = cp.getConstant(index).asInstanceOf[heap.MethodRef]
    val resolvedMethod =methodRef.resolvedMethod()
    if(!resolvedMethod.isStatic){
      throw new Exception("java.lang.IncompatibleClassChangeError")
    }
    val classStruct = resolvedMethod.classStruct
    if(!classStruct.initStarted){
      frame.revertNextPC()
      base.initClass(frame.thread, classStruct)
    }else{
      base.invokeMethod(frame, resolvedMethod)
    }
  }
}
