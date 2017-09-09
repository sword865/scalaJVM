package com.sword865.scalaJVM.instructions.stack

import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction
import com.sword865.scalaJVM.rtda.Frame

class SWAP extends NoOperandsInstruction{
  override def execute(frame: Frame): Unit = {
    val stack = frame.operandStack
    val slot1 = stack.popSlot()
    val slot2 = stack.popSlot()
    stack.pushSlot(slot1)
    stack.pushSlot(slot2)
  }
}
