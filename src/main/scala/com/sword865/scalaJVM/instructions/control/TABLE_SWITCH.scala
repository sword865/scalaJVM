package com.sword865.scalaJVM.instructions.control

import com.sword865.scalaJVM.instructions.base.{BytecodeReader, Instruction}
import com.sword865.scalaJVM.rtda.Frame

class TABLE_SWITCH extends Instruction{
  var defaultOffset: Int = 0
  var low: Int = 0
  var high: Int = 0
  var jumpOffsets: Array[Int] = _

  override def fetchOperands(reader: BytecodeReader): Unit = {
    reader.skipPadding()
    defaultOffset = reader.readInt32()
    low = reader.readInt32()
    high = reader.readInt32()
    val jumpOffsetsCount = high - low + 1
    jumpOffsets = reader.readInt32s(jumpOffsetsCount)
  }

  override def execute(frame: Frame): Unit = {
    val index = frame.operandStack.popInt()
    val offset = if(index >= low && index <= high){
      jumpOffsets(index-low)
    }else{
      defaultOffset
    }
    Instruction.branch(frame, offset)
  }

  override def toString: String =
    f"LOOKUP_SWITCH(defaultOffset=$defaultOffset, low=$low, high=$high, jumpOffsets=${jumpOffsets.mkString(",")}})"
}
