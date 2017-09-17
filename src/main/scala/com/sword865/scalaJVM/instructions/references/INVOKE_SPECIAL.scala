package com.sword865.scalaJVM.instructions.references

import com.sword865.scalaJVM.instructions.base
import com.sword865.scalaJVM.instructions.base.Index16Instruction
import com.sword865.scalaJVM.rtda.{Frame, heap}

class INVOKE_SPECIAL extends Index16Instruction{
  override def execute(frame: Frame): Unit = {
    val currentClass = frame.method.classStruct
    val cp = currentClass.constantPool
    val methodRef = cp.getConstant(index).asInstanceOf[heap.MethodRef]
    val resolvedClass = methodRef.resolvedClass()
    val resolvedMethod = methodRef.resolvedMethod()
    if(resolvedMethod.name == "<init>" && resolvedMethod.classStruct != resolvedClass) {
      throw new Exception("java.lang.NoSuchMethodError")
    }
    if(resolvedMethod.isStatic) {
      throw new Exception("java.lang.IncompatibleClassChangeError")
    }

    val ref = frame.operandStack.getRefFromTop(resolvedMethod.argSlotCount - 1)
    if(ref == null){
      throw new Exception("java.lang.NullPointerException")
    }

    if(resolvedMethod.isProtected &&
      resolvedMethod.classStruct.isSuperClassOf(currentClass) &&
      resolvedMethod.classStruct.getPackageName != currentClass.getPackageName &&
      ref.classStruct != currentClass &&
      !ref.classStruct.isSubClassOf(currentClass)){
      throw new Exception("java.lang.IllegalAccessError")
      }

    var methodToBeInvoked = if(currentClass.isSuper &&
      resolvedClass.isSuperClassOf(currentClass) &&
      resolvedMethod.name != "<init>") {
        heap.lookupMethodInClass(currentClass.superClass, methodRef.name, methodRef.descriptor)
    }else{
      resolvedMethod
    }

    if(methodToBeInvoked == null || methodToBeInvoked.isAbstract){
      throw new Exception("java.lang.AbstractMethodError")
    }
    base.invokeMethod(frame, methodToBeInvoked)
  }
}
