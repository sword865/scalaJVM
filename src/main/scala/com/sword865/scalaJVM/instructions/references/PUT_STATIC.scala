package com.sword865.scalaJVM.instructions.references

import com.sword865.scalaJVM.instructions.base
import com.sword865.scalaJVM.instructions.base.Index16Instruction
import com.sword865.scalaJVM.rtda.{Frame, heap}

class PUT_STATIC extends Index16Instruction{
  override def execute(frame: Frame): Unit = {
    val currentMethod = frame.method
    val currentClass = currentMethod.classStruct
    val cp = currentClass.constantPool
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
      if (field.isFinal) {
        if (currentClass != classStruct || currentMethod.name != "<clinit>") {
          throw new Exception("java.lang.IllegalAccessError")
        }
      }
      val descriptor = field.descriptor
      val slotId = field.slotId
      val slots = classStruct.staticVars
      val stack = frame.operandStack
      descriptor(0) match {
        case 'Z' | 'B' | 'C' | 'S' | 'I' =>
          slots.setInt(slotId, stack.popInt())
        case 'F' =>
          slots.setFloat(slotId, stack.popFloat())
        case 'J' =>
          slots.setLong(slotId, stack.popLong())
        case 'D' =>
          slots.setDouble(slotId, stack.popDouble())
        case 'L' | '[' =>
          slots.setRef(slotId, stack.popRef())
        case _ =>
        //TODO
      }
    }
  }
}
