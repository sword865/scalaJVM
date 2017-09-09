package com.sword865.scalaJVM.instructions.control

import com.sword865.scalaJVM.instructions.base.{BytecodeReader, Instruction}
import com.sword865.scalaJVM.rtda.Frame

class LOOKUP_SWITCH extends Instruction{
  var defaultOffset: Int = 0
  var npairs: Int = 0
  var matchOffsets: Array[Int] = _
  override def fetchOperands(reader: BytecodeReader): Unit = {
    reader.skipPadding()
    defaultOffset = reader.readInt32()
    npairs = reader.readInt32()
    matchOffsets = reader.readInt32s(npairs * 2)
  }

  override def execute(frame: Frame): Unit = {
    val key = frame.operandStack.popInt()
    var i = 0
    var flag = true
    while(flag && i < npairs * 2){
      if(matchOffsets(i) == key){
        val offset = matchOffsets(i+1)
        Instruction.branch(frame, offset)
        flag = false
      }
      i += 2
    }
    Instruction.branch(frame, defaultOffset)
  }
}
