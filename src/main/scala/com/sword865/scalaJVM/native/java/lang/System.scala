package com.sword865.scalaJVM.native.java.lang

/**
  * Created by tianhaowei on 2017/9/19.
  */
import com.sword865.scalaJVM.rtda.heap
import com.sword865.scalaJVM.{native, rtda}

object System {

  val jlSystem = "java/lang/System"

  def init(): Unit ={
    native.register(jlSystem, "arraycopy", "(Ljava/lang/Object;ILjava/lang/Object;II)V", arrayCopy)
  }


  def arrayCopy(frame: rtda.Frame): Unit ={
    val vars = frame.localVars
    val src = vars.getRef(0)
    val srcPos = vars.getInt(1)
    var dest = vars.getRef(2)
    val destPos = vars.getInt(3)
    val length = vars.getInt(4)

    if(src == null || dest ==null){
      throw new Exception("java.lang.NullPointerException")
    }
    if(!checkArrayCopy(src, dest)){
      throw new Exception("java.lang.ArrayStoreException")
    }
    if(srcPos<0 || destPos<0 || length<0|| srcPos+length>src.arrayLength|| destPos+length>dest.arrayLength){
      throw new Exception("java.lang.IndexOutOfBoundsException")
    }
    heap.Object.arrayCopy(src, dest, srcPos, destPos, length)
  }

  def checkArrayCopy(src: heap.Object, dest: heap.Object): Boolean = {
    val srcClass = src.classStruct
    val destClass = dest.classStruct
    if(!srcClass.isArray || !destClass.isArray){
      false
    }else{
      if(srcClass.componentClass().isPrimitive || destClass.componentClass().isPrimitive){
        srcClass == destClass
      }else{
        true
      }
    }
  }
}
