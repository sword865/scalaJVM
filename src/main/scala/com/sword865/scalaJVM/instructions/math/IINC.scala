package com.sword865.scalaJVM.instructions.math

import com.sword865.scalaJVM.instructions.base.{BytecodeReader, Instruction}
import com.sword865.scalaJVM.rtda.Frame

class IINC extends Instruction{
  var index : Int = 0
  var const : Int = 0

  override def fetchOperands(reader: BytecodeReader): Unit = {
    index = reader.readUInt8()
    const = reader.readInt8()
  }

  override def execute(frame: Frame): Unit = {
    val localVars = frame.localVars
    localVars.setInt(index, localVars.getInt(index) + const)
  }

  override def toString: String = f"IINC(index=$index, const=$const)"
}
