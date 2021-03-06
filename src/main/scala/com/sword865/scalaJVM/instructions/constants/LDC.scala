package com.sword865.scalaJVM.instructions.constants

import com.sword865.scalaJVM.instructions.base.{Index16Instruction, Index8Instruction}
import com.sword865.scalaJVM.rtda.{Frame, heap}

object LDC{

  def _ldc(frame: Frame, index: Int): Unit ={
    val stack = frame.operandStack
    val classStruct = frame.method.classStruct
    val c = classStruct.constantPool.getConstant(index)
    c match{
      case c: Int =>
        stack.pushInt(c)
      case c: Float =>
        stack.pushFloat(c)
      case c: String =>
        val internedStr = heap.StringPool.JString(classStruct.loader, c)
        stack.pushRef(internedStr)
      case c: heap.ClassRef =>
        val classObj = c.resolvedClass().jClass
        stack.pushRef(classObj)
      case _ => throw new Exception("todo: ldc!")
    }
  }
  class LDC extends Index8Instruction{
    override def execute(frame: Frame): Unit = {
      _ldc(frame, index)
    }
  }
  class LDC_W extends Index16Instruction{
    override def execute(frame: Frame): Unit = {
      _ldc(frame, index)
    }
  }
  class LDC2_W extends Index16Instruction{
    override def execute(frame: Frame): Unit = {
      val stack = frame.operandStack
      val cp = frame.method.classStruct.constantPool
      val c = cp.getConstant(index)
      c match{
        case c: Long =>
          stack.pushLong(c)
        case c: Double =>
          stack.pushDouble(c)
        case _ => throw new Exception("todo: ldc!")
      }
    }
  }
}



