package com.sword865.scalaJVM.rtda.heap

import scala.reflect.ClassTag

object Object{

  def apply(classStruct: ClassStruct): Object = {
    new Object(classStruct, Slots(classStruct.instanceSlotCount))
  }

  def apply[T](classStruct: ClassStruct, array:Array[T]): Object = {
    new Object(classStruct, array)
  }
}

class Object(val classStruct: ClassStruct, private val data: Any) {

  var extra: Any = _

  def isInstanceOf(classStruct: ClassStruct): Boolean ={
    classStruct.isAssignableFrom(classStruct)
  }

  def fields: Slots = {
    data.asInstanceOf[Slots]
  }

  //byte, short, int, long, char, float, double, object
  def arr[T](implicit ev: ClassTag[T]): Array[T] = {
    data.asInstanceOf[Array[T]]
  }

  def arrayLength: Int = {
    data match {
      case arr: Array[Byte] => arr.length
      case arr: Array[Short] => arr.length
      case arr: Array[Int] => arr.length
      case arr: Array[Long] => arr.length
      case arr: Array[Char] => arr.length
      case arr: Array[Float] => arr.length
      case arr: Array[Double] => arr.length
      case arr: Array[Object] => arr.length
      case _ => throw new Exception("Not a array!")
    }
  }

  def getRefVar(name: String, descriptor: String): Object={
    val field = classStruct.getField(name, descriptor, isStatic = false)
    val slots = data.asInstanceOf[Slots]
    slots.getRef(field.slotId)
  }

  def setRefVar(name: String, descriptor: String, ref: Object): Unit ={
    val field = classStruct.getField(name, descriptor, isStatic = false)
    val slots = data.asInstanceOf[Slots]
    slots.setRef(field.slotId, ref)
  }

}
