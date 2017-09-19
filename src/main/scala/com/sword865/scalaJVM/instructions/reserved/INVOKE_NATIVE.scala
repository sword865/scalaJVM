package com.sword865.scalaJVM.instructions.reserved

import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction
import com.sword865.scalaJVM.rtda.Frame
import com.sword865.scalaJVM.native
import com.sword865.scalaJVM.native.java.lang
import com.sword865.scalaJVM.native.sun.misc

/**
  * Created by tianhaowei on 2017/9/19.
  */

class INVOKE_NATIVE extends NoOperandsInstruction{
  lang.Class.init()
  lang.Object.init()
  lang.Float.init()
  lang.Double.init()
  lang.String.init()
  lang.System.init()
  lang.StackTraceElement.init()
  misc.VM.init()

  override def execute(frame: Frame): Unit = {
    val method = frame.method
    val className = method.classStruct.name
    val methodName = method.name
    val methodDescriptor = method.descriptor
    val nativeMethod = native.findNativeMethod(className, methodName, methodDescriptor)
    if(nativeMethod==null){
      val methodInfo = s"$className.$methodName$methodDescriptor"
      throw new Exception(s"java.lang.UnsatisfiedLinkError: $methodInfo")
    }
    nativeMethod(frame)
  }
}
