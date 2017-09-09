package com.sword865.scalaJVM.instructions.base


class BytecodeReader(var code: Array[Byte]=null , var pc: Int=0) {

  def reset(code0: Array[Byte], pc0: Int): Unit ={
    code = code0
    pc = pc0
  }

  def readInt8(): Byte = {
    val i = code(pc)
    pc += 1
    i
  }

  def readUInt8(): Short = {
    val i = code(pc)
    pc += 1
    (i.toShort & 0xFF).toShort
  }

  def readInt16(): Short = {
    val byte1 = readInt8()
    val byte2 = readInt8()
    ((byte1 << 8) | byte2).toShort
  }

  def readUInt16(): Int = {
    val byte1 = readUInt8()
    val byte2 = readUInt8()
    (byte1 << 8) | byte2
  }

  def readInt32(): Int = {
    val byte1 = readInt8()
    val byte2 = readInt8()
    val byte3 = readInt8()
    val byte4 = readInt8()
    (byte1 << 24) | (byte2 << 16) | (byte3 << 8) | byte4
  }

  def readInt32s(n: Int): Array[Int] = {
    (1 to n).map(_ => readInt32()).toArray
  }

  def skipPadding(): Unit = {
    while(pc % 4 != 0){
      readUInt8()
    }
  }


}
