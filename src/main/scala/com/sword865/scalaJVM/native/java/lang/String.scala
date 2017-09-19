package com.sword865.scalaJVM.native.java.lang

import com.sword865.scalaJVM.rtda.heap
import com.sword865.scalaJVM.{native, rtda}
/**
  * Created by tianhaowei on 2017/9/19.
  */
object String {

  val jlString = "java/lang/String"

  def init() {
    native.register(jlString, "intern", "()Ljava/lang/String;", intern)
  }

  // public native String intern();
  // ()Ljava/lang/String;
  def intern(frame: rtda.Frame) {
    val thisClass = frame.localVars.getThis
    val interned = heap.StringPool.internString(thisClass)
    frame.operandStack.pushRef(interned)
  }

}
