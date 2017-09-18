package com.sword865.scalaJVM.instructions.extended

import com.sword865.scalaJVM.instructions.base.{BranchInstruction, Instruction}
import com.sword865.scalaJVM.rtda.{Frame, heap}

object IFYNNULL{
  class IFNULL extends IFYNNULL{
    override def cond(value: heap.Object): Boolean = value == null
  }
  class IFNONNULL extends IFYNNULL{
    override def cond(value: heap.Object): Boolean = value != null
  }
}


abstract class IFYNNULL extends BranchInstruction {

  def cond(value: heap.Object): Boolean

  override def execute(frame: Frame): Unit = {
    val stack = frame.operandStack
    val value = stack.popRef()
    if(cond(value)){
      Instruction.branch(frame, offset)
    }
  }

}
