package com.sword865.scalaJVM.instructions.references

import com.sword865.scalaJVM.instructions.base
import com.sword865.scalaJVM.instructions.base.{BytecodeReader, Instruction}
import com.sword865.scalaJVM.rtda.{Frame, heap}

class INVOKE_INTERFACE extends Instruction{

  var index: Int = 0

  override def fetchOperands(reader: BytecodeReader): Unit = {
    index = reader.readUInt16()
    reader.readUInt8()  //count
    reader.readUInt8()  //must be zero
  }

  override def execute(frame: Frame): Unit = {
    val cp = frame.method.classStruct.constantPool
    val methodRef = cp.getConstant(index).asInstanceOf[heap.InterfaceMethodRef]
    val resolvedMethod = methodRef.resolvedInterfaceMethod
    if(resolvedMethod.isStatic || resolvedMethod.isPrivate){
      throw new Exception("java.lang.IncompatibleClassChangeError")
    }

    val ref = frame.operandStack.getRefFromTop(resolvedMethod.argSlotCount - 1)
    if(ref == null){
      throw new Exception("java.lang.NullPointerException") // todo
    }
    if(!ref.classStruct.isImplements(methodRef.resolvedClass())){
      throw new Exception("java.lang.IncompatibleClassChangeError")
    }

    val methodToBeInvoked = heap.lookupMethodInClass(ref.classStruct, methodRef.name, methodRef.descriptor)
    if(methodToBeInvoked == null || methodToBeInvoked.isAbstract){
      throw new Exception("java.lang.AbstractMethodError")
    }
    if(!methodToBeInvoked.isPublic){
      throw new Exception("java.lang.IllegalAccessError")
    }

    base.invokeMethod(frame, methodToBeInvoked)
  }

  override def toString: String = s"INVOKE_INTERFACE(index=$index)"
}
