package com.sword865.scalaJVM.instructions.constants

import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction
import com.sword865.scalaJVM.rtda.Frame

class NOP extends NoOperandsInstruction{
  override def execute(frame: Frame): Unit = {}
}
