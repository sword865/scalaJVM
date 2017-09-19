package com.sword865.scalaJVM.instructions.references

import com.sword865.scalaJVM.instructions.base.Index16Instruction
import com.sword865.scalaJVM.rtda.{Frame, heap}

class PUT_FIELD extends Index16Instruction{
  override def execute(frame: Frame): Unit = {
    val currentMethod = frame.method
    val currentClass = currentMethod.classStruct
    val cp = currentClass.constantPool
    val fieldRef = cp.getConstant(index).asInstanceOf[heap.FieldRef]
    val field = fieldRef.resolvedField()

    if (field.isStatic) {
      throw new Exception("java.lang.IncompatibleClassChangeError")
    }
    if (field.isFinal) {
      if (currentClass != field.classStruct || currentMethod.name != "<init>") {
        throw new Exception("java.lang.IllegalAccessError")
      }
    }
    val descriptor = field.descriptor
    val slotId = field.slotId
    val stack = frame.operandStack
    descriptor(0) match {
      case 'Z' | 'B' | 'C' | 'S' | 'I' =>
        val value = stack.popInt()
        val ref = stack.popRef()
        if(ref == null){
          throw new Exception("java.lang.NullPointerException")
        }
        ref.fields.setInt(slotId, value)
      case 'F' =>
        val value = stack.popFloat()
        val ref = stack.popRef()
        if(ref == null){
          throw new Exception("java.lang.NullPointerException")
        }
        ref.fields.setFloat(slotId, value)
      case 'J' =>
        val value = stack.popLong()
        val ref = stack.popRef()
        if(ref == null){
          throw new Exception("java.lang.NullPointerException")
        }
        ref.fields.setLong(slotId, value)
      case 'D' =>
        val value = stack.popDouble()
        val ref = stack.popRef()
        if(ref == null){
          throw new Exception("java.lang.NullPointerException")
        }
        ref.fields.setDouble(slotId, value)
      case 'L' | '[' =>
        val value = stack.popRef()
        val ref = stack.popRef()
        if(ref == null){
          throw new Exception("java.lang.NullPointerException")
        }
        ref.fields.setRef(slotId, value)
      case _ =>
      //TODO
    }
  }
}
