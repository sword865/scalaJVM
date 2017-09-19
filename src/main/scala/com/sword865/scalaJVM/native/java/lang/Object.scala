package com.sword865.scalaJVM.native.java.lang

/**
  * Created by tianhaowei on 2017/9/19.
  */
import com.sword865.scalaJVM.{native, rtda}

object Object {

  val jlObject = "java/lang/Object"

  def init() {
    native.register(jlObject, "getClass", "()Ljava/lang/Class;", stackGetClass)
    native.register(jlObject, "hashCode", "()I", hashCode)
    native.register(jlObject, "clone", "()Ljava/lang/Object;", clone)
  }

  def stackGetClass(frame: rtda.Frame): Unit ={
    val thisClass = frame.localVars.getThis
    val classStruct = thisClass.classStruct.jClass
    frame.operandStack.pushRef(classStruct)
  }

  def hashCode(frame: rtda.Frame): Unit ={
    val thisClass = frame.localVars.getThis
    val hash = thisClass.hashCode()
    frame.operandStack.pushInt(hash)
  }

  def clone(frame: rtda.Frame): Unit = {
    val thisClass = frame.localVars.getThis
    val cloneable = thisClass.classStruct.loader.loadClass("java/lang/Cloneable")
    if(!thisClass.classStruct.isImplements(cloneable)){
      throw new Exception("java.lang.CloneNotSupportedException")
    }
    frame.operandStack.pushRef(thisClass.clone())
  }

}
