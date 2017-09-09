package com.sword865.scalaJVM.instructions.extended

import com.sword865.scalaJVM.instructions.base.{BytecodeReader, Instruction}
import com.sword865.scalaJVM.rtda.Frame
import com.sword865.scalaJVM.instructions.stores.STORE._
import com.sword865.scalaJVM.instructions.loads.LOAD._
import com.sword865.scalaJVM.instructions.math.IINC


class WIDE extends Instruction{
  var modifiedInstruction: Instruction = _

  override def fetchOperands(reader: BytecodeReader): Unit = {
    val opcode = reader.readUInt8()
    opcode match{
      case 0x15 =>
        val inst = new ILOAD()
        inst.index = reader.readUInt16()
        modifiedInstruction = inst
      case 0x16 =>
        val inst = new LLOAD()
        inst.index = reader.readUInt16()
        modifiedInstruction = inst
      case 0x17 =>
        val inst = new FLOAD()
        inst.index = reader.readUInt16()
        modifiedInstruction = inst
      case 0x18 =>
        val inst = new DLOAD()
        inst.index = reader.readUInt16()
        modifiedInstruction = inst
      case 0x19 =>
        val inst = new ALOAD()
        inst.index = reader.readUInt16()
        modifiedInstruction = inst
      case 0x36 =>
        val inst = new ISTORE()
        inst.index = reader.readUInt16()
        modifiedInstruction = inst
      case 0x37 =>
        val inst = new LSTORE()
        inst.index = reader.readUInt16()
        modifiedInstruction = inst
      case 0x38 =>
        val inst = new FSTORE()
        inst.index = reader.readUInt16()
        modifiedInstruction = inst
      case 0x39 =>
        val inst = new DSTORE()
        inst.index = reader.readUInt16()
        modifiedInstruction = inst
      case 0x3a =>
        val inst = new ASTORE()
        inst.index = reader.readUInt16()
        modifiedInstruction = inst
      case 0x84 =>
        val inst = new IINC()
        inst.index = reader.readUInt16()
        inst.const = reader.readInt16()
        modifiedInstruction = inst
      case 0x09 =>
        throw new Exception("Unsupported opcode: 0xa9!")
    }
  }

  override def execute(frame: Frame): Unit = {
    modifiedInstruction.execute(frame)
  }
}
