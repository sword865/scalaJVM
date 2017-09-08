package com.sword865.scalaJVM.rtda

import java.nio.ByteBuffer

/**
  * Created by tianhaowei on 2017/9/8.
  */

object LocalVars {
  def apply(maxLocals: Int): LocalVars = {
    if(maxLocals > 0){
      new LocalVars(new Array[Any](maxLocals))
    }else{
      null
    }
  }
}

class LocalVars(slots: Array[Any]){

  def apply(i: Int): Any = slots(i)

  def update(i:Int, value: Any): Unit = {
    slots(i) = value
  }

  def setInt(index: Int, value: Int): Unit ={
    slots(index) = value
  }

  def getInt(index: Int): Int = {
    slots(index).asInstanceOf[Int]
  }

  def setFloat(index: Int, value: Float): Unit = {
    val bytes = ByteBuffer.allocate(4).putFloat(value).array()
    slots(index) = ByteBuffer.wrap(bytes).getInt()
  }

  def getFloat(index: Int): Float = {
    val bytes = ByteBuffer.allocate(4).putInt(slots(index).asInstanceOf[Int]).array()
    ByteBuffer.wrap(bytes).getFloat()
  }

  def setLong(index: Int, value: Long): Unit = {
    val bytes = ByteBuffer.allocate(8).putLong(value).array()
    val wrap = ByteBuffer.wrap(bytes)
    slots(index+1) = wrap.getInt
    slots(index) = wrap.getInt
  }

  def getLong(index: Int): Long = {
    val high = slots(index + 1).asInstanceOf[Int]
    val low = slots(index).asInstanceOf[Int]
    val bytes = ByteBuffer.allocate(8).putInt(high).putInt(low).array()
    ByteBuffer.wrap(bytes).getLong()
  }

  def setDouble(index: Int, value: Double): Unit = {
    val bytes = ByteBuffer.allocate(8).putDouble(value).array()
    val wrap = ByteBuffer.wrap(bytes)
    slots(index+1) = wrap.getInt
    slots(index) = wrap.getInt
  }

  def getDouble(index: Int): Double = {
    val low = slots(index).asInstanceOf[Int]
    val high = slots(index + 1).asInstanceOf[Int]
    val bytes = ByteBuffer.allocate(8).putInt(high).putInt(low).array()
    ByteBuffer.wrap(bytes).getDouble()
  }

  def setRef(index: Int, value: AnyRef): Unit = {
    slots(index) = value
  }

  def getRef(index: Int): AnyRef = {
    slots(index).asInstanceOf[AnyRef]
  }

}
