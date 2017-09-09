package com.sword865.scalaJVM.instructions.comparisons

import com.sword865.scalaJVM.instructions.base.{BranchInstruction, Instruction}
import com.sword865.scalaJVM.rtda.Frame


object IFCOND {
  class IFEQ extends IFCOND{
    override def cond(value: Int): Boolean = value == 0
  }
  class IFNE extends IFCOND{
    override def cond(value: Int): Boolean = value != 0
  }
  class IFLT extends IFCOND{
    override def cond(value: Int): Boolean = value < 0
  }
  class IFLE extends IFCOND{
    override def cond(value: Int): Boolean = value <= 0
  }
  class IFGT extends IFCOND{
    override def cond(value: Int): Boolean = value > 0
  }
  class IFGE extends IFCOND{
    override def cond(value: Int): Boolean = value >= 0
  }
}

abstract class IFCOND extends BranchInstruction {

  def cond(value: Int): Boolean

  override def execute(frame: Frame): Unit = {
    val stack = frame.operandStack
    val value = stack.popInt()
    if(cond(value)){
      Instruction.branch(frame, offset)
    }
  }
}
