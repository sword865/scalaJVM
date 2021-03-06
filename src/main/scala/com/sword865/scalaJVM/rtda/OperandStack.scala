package com.sword865.scalaJVM.rtda

import java.nio.ByteBuffer

import scala.reflect.ClassTag

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
  override def toString: String = f"OperandStack(size=$size, value=${slots.mkString(",")})"

  def pop[T](implicit ev: ClassTag[T]): T ={
    //print(s"pop $ev, size=$size  ")
    val value = if(ev == manifest[Boolean]){
      popBoolean()
    } else if(ev == manifest[Byte]) {
     popInt().toByte
    }else if(ev == manifest[Char]){
     popInt().toChar
    }else if(ev == manifest[Short]) {
      popInt().toShort
    }else if(ev == manifest[Int]){
      popInt()
    }else if(ev ==manifest[Float]){
      popFloat()
    }else if(ev == manifest[Double]){
      popDouble()
    }else if(ev == manifest[Long]) {
      popLong()
    }else if(ev == manifest[heap.Object]) {
      popRef()
    }else{
      throw new Exception(s"unknow pop type")
    }
    //println(s"after pop $ev, size=$size")
    value.asInstanceOf[T]
  }

  def push[T](value: T)(implicit ev: ClassTag[T]): Unit ={
    //print(s"push $ev, size=$size  ")
    if(ev == manifest[Boolean]) {
      pushBoolean(value.asInstanceOf[Boolean])
    } else if(ev == manifest[Byte]) {
      pushInt(value.asInstanceOf[Byte].toInt)
    }else if(ev == manifest[Char]){
      pushInt(value.asInstanceOf[Char].toInt)
    }else if(ev == manifest[Short]){
      pushInt(value.asInstanceOf[Short].toInt)
    }else if(ev == manifest[Int]){
      pushInt(value.asInstanceOf[Int])
    }else if(ev ==manifest[Float]){
      pushFloat(value.asInstanceOf[Float])
    }else if(ev == manifest[Double]){
      pushDouble(value.asInstanceOf[Double])
    }else if(ev == manifest[Long]) {
      pushLong(value.asInstanceOf[Long])
    }else if(ev == manifest[heap.Object]) {
      pushRef(value.asInstanceOf[heap.Object])
    }else{
      throw new Exception(s"unknow push type: $value")
    }
    //println(s"after push $ev, size=$size")
  }

  def pushBoolean(value: Boolean): Unit ={
    if(value) pushInt(1) else pushInt(0)
  }

  def popBoolean(): Boolean = {
    popInt() == 1
  }

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

  def pushRef(value: heap.Object): Unit = {
    slots(size) = value
    size += 1
  }

  def popRef(): heap.Object = {
    size -= 1
    slots(size).asInstanceOf[heap.Object]
  }

  def pushSlot(value: Any): Unit = {
    slots(size) = value
    size += 1
  }

  def popSlot(): Any = {
    size -= 1
    slots(size)
  }

  def getRefFromTop(n:Int): heap.Object = {
    slots(size-1-n).asInstanceOf[heap.Object]
  }

  def clear(): Unit ={
    size = 0
    for(i<-slots.indices){
      slots(i) = null
    }
  }
}
