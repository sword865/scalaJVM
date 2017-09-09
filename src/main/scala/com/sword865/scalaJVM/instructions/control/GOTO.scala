package com.sword865.scalaJVM.instructions.control

import com.sword865.scalaJVM.instructions.base.{BranchInstruction, Instruction}
import com.sword865.scalaJVM.rtda.Frame

class GOTO extends BranchInstruction {
  override def execute(frame: Frame): Unit = {
    Instruction.branch(frame, offset)
  }
}