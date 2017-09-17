package com.sword865.scalaJVM.instructions.references

import com.sword865.scalaJVM.instructions.base
import com.sword865.scalaJVM.instructions.base.Index16Instruction
import com.sword865.scalaJVM.rtda.{Frame, heap}

class GET_STATIC extends Index16Instruction{
  override def execute(frame: Frame): Unit = {
    val cp = frame.method.classStruct.constantPool
    val fieldRef = cp.getConstant(index).asInstanceOf[heap.FieldRef]
    val field = fieldRef.resolvedField()
    val classStruct = field.classStruct
    if(!classStruct.initStarted){
      frame.revertNextPC()
      base.initClass(frame.thread, classStruct)
    }else {
      if (!field.isStatic) {
        throw new Exception("java.lang.IncompatibleClassChangeError")
      }

      val descriptor = field.descriptor
      val slotId = field.slotId
      val slots = classStruct.staticVars
      val stack = frame.operandStack
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
}
