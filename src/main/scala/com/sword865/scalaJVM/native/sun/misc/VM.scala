package com.sword865.scalaJVM.native.sun.misc

/**
  * Created by tianhaowei on 2017/9/19.
  */
import com.sword865.scalaJVM.instructions.base
import com.sword865.scalaJVM.rtda.heap
import com.sword865.scalaJVM.{native, rtda}

object VM {

  def init(): Unit ={
    native.register("sun/misc/VM", "initialize", "()V", initialize)
  }

  def initialize(frame: rtda.Frame): Unit ={
    val vmClass = frame.method.classStruct
    val savedProps = vmClass.getRefVar("savedProps", "Ljava/util/Properties;")
    val key = heap.StringPool.JString(vmClass.loader, "foo")
    val value = heap.StringPool.JString(vmClass.loader, "bar")

    frame.operandStack.pushRef(savedProps)
    frame.operandStack.pushRef(key)
    frame.operandStack.pushRef(value)


    val propsClass = vmClass.loader.loadClass("java/util/Properties")
    val setPropMethod = propsClass.getInstanceMethod("setProperty",
      "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;")
    base.invokeMethod(frame, setPropMethod)
  }
}
