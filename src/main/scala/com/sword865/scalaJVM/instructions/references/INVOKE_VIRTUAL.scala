package com.sword865.scalaJVM.instructions.references

import com.sword865.scalaJVM.instructions.base.Index16Instruction
import com.sword865.scalaJVM.rtda.{Frame, heap}

class INVOKE_VIRTUAL extends Index16Instruction{
  override def execute(frame: Frame): Unit = {
    val cp = frame.method.classStruct.constantPool
    val constant = cp.getConstant(index)
    val methodRef = constant.asInstanceOf[heap.MethodRef]
    if(methodRef.name == "println"){
      val stack = frame.operandStack
      methodRef.descriptor match {
        case "(Z)V" =>
          println(stack.popInt() != 0)
        case "(C)V" =>
          println(stack.popInt())
        case "(I)V"|"(B)V"|"(S)V" =>
          println(stack.popInt())
        case "(F)V" =>
          println(stack.popFloat())
        case "(J)V" =>
          println(stack.popLong())
        case "(D)V" =>
          println(stack.popDouble())
        case _=>
          println("println: " + methodRef.descriptor)
      }
      stack.popRef()
    }
  }
}
