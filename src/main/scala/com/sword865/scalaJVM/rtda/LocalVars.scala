package com.sword865.scalaJVM.rtda

import java.nio.ByteBuffer
import scala.reflect.ClassTag
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

  override def toString: String = f"LocalVars(${slots.mkString(",")})"

  override def clone(): AnyRef = new LocalVars(slots.clone())

  def apply(i: Int): Any = slots(i)

  def update(i:Int, value: Any): Unit = {
    slots(i) = value
  }

  def get[T](index: Int)(implicit ev: ClassTag[T]): T ={
    val value = if(ev == manifest[Int]){
      getInt(index)
    }else if(ev ==manifest[Float]){
      getFloat(index)
    }else if(ev == manifest[Double]){
      getDouble(index)
    }else if(ev == manifest[Long]) {
      getLong(index)
    }else{
      getRef(index)
    }
    value.asInstanceOf[T]
  }

  def set[T](index: Int, value: T)(implicit ev: ClassTag[T]): Unit ={
    if(ev == manifest[Int]){
      setInt(index, value.asInstanceOf[Int])
    }else if(ev ==manifest[Float]){
      setFloat(index, value.asInstanceOf[Float])
    }else if(ev == manifest[Double]){
      setDouble(index, value.asInstanceOf[Double])
    }else if(ev == manifest[Long]) {
      setLong(index, value.asInstanceOf[Long])
    }else{
      setRef(index, value.asInstanceOf[heap.Object])
    }
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

  def setRef(index: Int, value: heap.Object): Unit = {
    slots(index) = value
  }

  def getRef(index: Int): heap.Object = {
    slots(index).asInstanceOf[heap.Object]
  }

  def setSlot(index: Int, value: Any): Unit = {
    slots(index) = value
  }

  def getThis: heap.Object = getRef(0)
}
