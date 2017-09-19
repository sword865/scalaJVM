package com.sword865.scalaJVM.rtda.heap

import scala.reflect.ClassTag

object Object{

  def arrayCopy(src: Object, dest: Object, srcPos: Int, destPos: Int, length: Int): Unit = {
    src.data match{
      case arr: Array[Byte] =>
        val arr2 = dest.data.asInstanceOf[Array[Byte]]
        arr.slice(srcPos, srcPos+length).copyToArray(arr2, destPos, length)
      case arr: Array[Short] =>
        val arr2 = dest.data.asInstanceOf[Array[Short]]
        arr.slice(srcPos, srcPos+length).copyToArray(arr2, destPos, length)
      case arr: Array[Int] =>
        val arr2 = dest.data.asInstanceOf[Array[Int]]
        arr.slice(srcPos, srcPos+length).copyToArray(arr2, destPos, length)
      case arr: Array[Long] =>
        val arr2 = dest.data.asInstanceOf[Array[Long]]
        arr.slice(srcPos, srcPos+length).copyToArray(arr2, destPos, length)
      case arr: Array[Char] =>
        val arr2 = dest.data.asInstanceOf[Array[Char]]
        arr.slice(srcPos, srcPos+length).copyToArray(arr2, destPos, length)
      case arr: Array[Float] =>
        val arr2 = dest.data.asInstanceOf[Array[Float]]
        arr.slice(srcPos, srcPos+length).copyToArray(arr2, destPos, length)
      case arr: Array[Double] =>
        val arr2 = dest.data.asInstanceOf[Array[Double]]
        arr.slice(srcPos, srcPos+length).copyToArray(arr2, destPos, length)
      case arr: Array[Object] =>
        val arr2 = dest.data.asInstanceOf[Array[Object]]
        arr.slice(srcPos, srcPos+length).copyToArray(arr2, destPos, length)
      case _ => throw new Exception("Not a array!")
    }
  }


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

  def cloneData(): Any = {
    data match{
      case arr: Array[Byte] => arr.clone()
      case arr: Array[Short] => arr.clone()
      case arr: Array[Int] => arr.clone()
      case arr: Array[Long] => arr.clone()
      case arr: Array[Char] => arr.clone()
      case arr: Array[Float] => arr.clone()
      case arr: Array[Double] => arr.clone()
      case arr: Array[Object] => arr.clone()
      case slot: Slots => slot.clone()
    }
  }

  override def clone(): Object ={
    new Object(classStruct, cloneData())
  }

}
