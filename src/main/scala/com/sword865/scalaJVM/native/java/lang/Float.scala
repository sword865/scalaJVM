package com.sword865.scalaJVM.native.java.lang

/**
  * Created by tianhaowei on 2017/9/19.
  */
import com.sword865.scalaJVM.rtda.heap
import com.sword865.scalaJVM.{native, rtda}

object Float {

  val jlFloat = "java/lang/Float"

  def init() {
    native.register(jlFloat, "floatToRawIntBits", "(F)I", floatToRawIntBits)
    native.register(jlFloat, "intBitsToFloat", "(I)F", intBitsToFloat)
  }


  // public static native long doubleToRawLongBits(double value);
  // (D)J
  def floatToRawIntBits(frame: rtda.Frame) {
    val value = frame.localVars.getFloat(0)
    val bits = java.lang.Float.floatToIntBits(value) // todo
    frame.operandStack.pushInt(bits)
  }

  // public static native double longBitsToDouble(long bits);
  // (J)D
  def intBitsToFloat(frame: rtda.Frame) {
    val bits = frame.localVars.getInt(0)
    val value = java.lang.Float.intBitsToFloat(bits) // todo
    frame.operandStack.pushFloat(value)
  }

}
