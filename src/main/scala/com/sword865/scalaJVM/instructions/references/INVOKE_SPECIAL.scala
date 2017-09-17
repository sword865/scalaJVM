package com.sword865.scalaJVM.instructions.references

import com.sword865.scalaJVM.instructions.base.Index16Instruction
import com.sword865.scalaJVM.rtda.Frame

class INVOKE_SPECIAL extends Index16Instruction{
  override def execute(frame: Frame): Unit = {
    frame.operandStack.popRef()
  }
}
