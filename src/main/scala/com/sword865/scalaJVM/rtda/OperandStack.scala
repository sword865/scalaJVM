package com.sword865.scalaJVM.rtda

import java.nio.ByteBuffer

/**
  * Created by tianhaowei on 2017/9/8.
  */

object OperandStack {
  def apply(maxStack: Int): OperandStack = {
    if(maxStack > 0){
      new OperandStack(new Array[Any](maxStack))
    }else{
      null
    }
  }
}

class OperandStack(slots: Array[Any], var size: Int = 0) {

  def pushInt(value: Int): Unit ={
    slots(size) = value
    size += 1
  }

  def popInt(): Int = {
    size -= 1
    slots(size).asInstanceOf[Int]
  }

  def pushFloat(value: Float): Unit = {
    val bytes = ByteBuffer.allocate(4).putFloat(value).array()
    slots(size) = ByteBuffer.wrap(bytes).getInt()
    size += 1
  }

  def popFloat(): Float = {
    size -= 1
    val bytes = ByteBuffer.allocate(4).putInt(slots(size).asInstanceOf[Int]).array()
    ByteBuffer.wrap(bytes).getFloat()
  }

  def pushLong(value: Long): Unit = {
    val bytes = ByteBuffer.allocate(8).putLong(value).array()
    val wrap = ByteBuffer.wrap(bytes)
    slots(size+1) = wrap.getInt
    slots(size) = wrap.getInt
    size += 2
  }

  def popLong(): Long = {
    size -= 2
    val high = slots(size + 1).asInstanceOf[Int]
    val low = slots(size).asInstanceOf[Int]
    val bytes = ByteBuffer.allocate(8).putInt(high).putInt(low).array()
    ByteBuffer.wrap(bytes).getLong()
  }

  def pushDouble(value: Double): Unit = {
    val bytes = ByteBuffer.allocate(8).putDouble(value).array()
    val wrap = ByteBuffer.wrap(bytes)
    slots(size+1) = wrap.getInt
    slots(size) = wrap.getInt
    size += 2
  }

  def popDouble(): Double = {
    size -= 2
    val low = slots(size).asInstanceOf[Int]
    val high = slots(size + 1).asInstanceOf[Int]
    val bytes = ByteBuffer.allocate(8).putInt(high).putInt(low).array()
    ByteBuffer.wrap(bytes).getDouble()
  }

  def pushRef(value: AnyRef): Unit = {
    slots(size) = value
    size += 1
  }

  def popRef(): AnyRef = {
    size -= 1
    slots(size).asInstanceOf[AnyRef]
  }
  
}
