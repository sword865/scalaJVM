package com.sword865.scalaJVM.instructions.references

import com.sword865.scalaJVM.instructions.base
import com.sword865.scalaJVM.instructions.base.Index16Instruction
import com.sword865.scalaJVM.rtda.{Frame, heap}
import com.sword865.scalaJVM.rtda

class INVOKE_VIRTUAL extends Index16Instruction {
  override def execute(frame: Frame): Unit = {
    val currentClass = frame.method.classStruct
    val cp = currentClass.constantPool
    val methodRef = cp.getConstant(index).asInstanceOf[heap.MethodRef]
    val resolvedMethod = methodRef.resolvedMethod()
    if(resolvedMethod.isStatic){
      throw new Exception("java.lang.IncompatibleClassChangeError")
    }
    val ref = frame.operandStack.getRefFromTop(resolvedMethod.argSlotCount - 1)
    if(ref == null){
      // hack!
      if(methodRef.name == "println"){
        _println(frame.operandStack, methodRef.descriptor)
      }else {
        throw new Exception("java.lang.NullPointerException")
      }
    }else {
      if(resolvedMethod.isProtected &&
        resolvedMethod.classStruct.isSuperClassOf(currentClass) &&
        resolvedMethod.classStruct.getPackageName != currentClass.getPackageName &&
        ref.classStruct != currentClass &&
        !ref.classStruct.isSubClassOf(currentClass)){
        throw new Exception("java.lang.IllegalAccessError")
      }
      val methodToBeInvoked = heap.lookupMethodInClass(ref.classStruct, methodRef.name, methodRef.descriptor)
      if(methodToBeInvoked == null || methodToBeInvoked.isAbstract){
        throw new Exception("java.lang.AbstractMethodError")
      }
      base.invokeMethod(frame, methodToBeInvoked)
    }
  }

  def _println(stack: rtda.OperandStack, descriptor: String): Unit = {
    descriptor match {
      case "(Z)V" =>
        println(stack.popInt() != 0)
      case "(C)V" =>
        println(stack.popInt())
      case "(I)V" | "(B)V" | "(S)V" =>
        println(stack.popInt())
      case "(F)V" =>
        println(stack.popFloat())
      case "(J)V" =>
        println(stack.popLong())
      case "(D)V" =>
        println(stack.popDouble())
      case _ =>
        println("println: " + descriptor)
    }
    stack.popRef()
  }
}
