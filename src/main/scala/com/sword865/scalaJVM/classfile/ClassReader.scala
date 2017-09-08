package com.sword865.scalaJVM.classfile

import java.nio.{ByteBuffer, ByteOrder}

/**
  * Created by tianhaowei on 2017/9/6.
  */

object  ClassReader {
  def apply(data: Array[Byte]): ClassReader = new ClassReader(data)
}

class ClassReader(data: Array[Byte]) {

  val bb: ByteBuffer = ByteBuffer.wrap(data)
  bb.order(ByteOrder.BIG_ENDIAN)

  def readByte(): Byte = bb.get()

  def readChar(): Char = bb.getChar()

  def readUInt16(): Int = {
    bb.getChar().toInt
  }

  def readInt(): Int = bb.getInt()

  def readUInt32(): Long = {
    val value = bb.getInt()
    value & 0x00000000ffffffffL
  }

  def readFloat(): Float = bb.getFloat()

  def readLong(): Long = bb.getLong()

  def readDouble(): Double = bb.getDouble

  def readChars(): Array[Char] ={
    val n = readUInt16()
    (1 to n).map(_ => readChar()).toArray
  }

  def readUInt16s(): Array[Int] ={
    readChars().map(_.toInt)
  }

  def readBytes(n: Long): Array[Byte] = {
    (1L to n).map(_ => readByte()).toArray
  }

}
