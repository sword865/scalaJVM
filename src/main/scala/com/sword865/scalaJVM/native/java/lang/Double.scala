package com.sword865.scalaJVM.native.java.lang

import com.sword865.scalaJVM.{native, rtda}

/**
  * Created by tianhaowei on 2017/9/19.
  */
object Double {
  val jlDouble = "java/lang/Double"

  def init(): Unit ={
    native.register(jlDouble, "doubleToRawLongBits", "(D)J", doubleToRawLongBits)
    native.register(jlDouble, "longBitsToDouble", "(J)D", longBitsToDouble)
  }

  // public static native long doubleToRawLongBits(double value);
  // (D)J
  def doubleToRawLongBits(frame: rtda.Frame) {
    val value = frame.localVars.getDouble(0)
    val bits = java.lang.Double.doubleToLongBits(value) // todo
    frame.operandStack.pushLong(bits)
  }

  // public static native double longBitsToDouble(long bits);
  // (J)D
  def longBitsToDouble(frame: rtda.Frame) {
    val bits = frame.localVars.getLong(0)
    val value = java.lang.Double.longBitsToDouble(bits) // todo
    frame.operandStack.pushDouble(value)
  }

}
