package com.sword865.scalaJVM.instructions.references

import com.sword865.scalaJVM.instructions.base.Index16Instruction
import com.sword865.scalaJVM.rtda.{Frame, heap}

class GET_FIELD extends Index16Instruction{
  override def execute(frame: Frame): Unit = {
    val cp = frame.method.classStruct.constantPool
    val fieldRef = cp.getConstant(index).asInstanceOf[heap.FieldRef]
    val field = fieldRef.resolvedField()
    if(field.isStatic){
      throw new Exception("java.lang.IncompatibleClassChangeError")
    }

    val stack = frame.operandStack
    val ref = stack.popRef()
    if(ref==null){
      throw new Exception("java.lang.NullPointerException")
    }
    val descriptor = field.descriptor
    val slotId = field.slotId
    val slots = ref.fields

    descriptor(0) match {
      case 'Z' | 'B' | 'C' | 'S' | 'I' =>
        stack.pushInt(slots.getInt(slotId))
      case 'F' =>
        stack.pushFloat(slots.getFloat(slotId))
      case 'J' =>
        stack.pushLong(slots.getLong(slotId))
      case 'D' =>
        stack.pushDouble(slots.getDouble(slotId))
      case 'L' | '[' =>
        stack.pushRef(slots.getRef(slotId))
      case _ =>
      //TODO
    }
  }
}
