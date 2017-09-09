package com.sword865.scalaJVM.instructions.extended

import com.sword865.scalaJVM.instructions.base.{BytecodeReader, Instruction}
import com.sword865.scalaJVM.rtda.Frame

class GOTO_W extends Instruction{
  var offset: Int = 0

  override def fetchOperands(reader: BytecodeReader): Unit = {
    offset = reader.readInt32()
  }

  override def execute(frame: Frame): Unit = {
    Instruction.branch(frame, offset)
  }
}
