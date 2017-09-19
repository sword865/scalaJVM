package com.sword865.scalaJVM.instructions.references

import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction
import com.sword865.scalaJVM.{native, rtda}
import com.sword865.scalaJVM.rtda.{Frame, heap}

/**
  * Created by tianhaowei on 2017/9/19.
  */
class ATHROW extends NoOperandsInstruction{

  override def execute(frame: Frame): Unit = {
    val ex = frame.operandStack.popRef()
    if(ex == null){
      throw new Exception("java.lang.NullPointerException")
    }
    val thread = frame.thread
    if(!findAndGotoExceptionHandler(thread, ex)){
      handleUncaughtException(thread, ex)
    }
  }

  def findAndGotoExceptionHandler(thread: rtda.Thread, ex: heap.Object): Boolean = {
    var flag = true
    var result = false
    while(flag){
      val frame = thread.currentFrame()
      val pc = frame.nextPC-1
      val handlerPC =  frame.method.findExceptionHandler(ex.classStruct, pc)
      if(handlerPC > 0){
        val stack = frame.operandStack
        stack.clear()
        stack.pushRef(ex)
        frame.nextPC = handlerPC
        result = true
        flag = false
      }else{
        thread.popFrame()
        if(thread.isStackEmpty){
          flag = false
        }
      }
    }
    result
  }

  def handleUncaughtException(thread: rtda.Thread, ex: heap.Object): Unit = {
    thread.clearStack()

    val jMsg = ex.getRefVar("detailMessage", "Ljava/lang/String;")
    val msg = heap.StringPool.getString(jMsg)
    println(ex.classStruct.javaName + ": " + msg)

    //TODO: fix print
    val stes = ex.extra.asInstanceOf[Array[native.java.lang.StackTraceElement]]
    stes.foreach(ste => {
      println(s"\t${ste.className}.${ste.methodName}:${ste.fileName}:${ste.lineNumber}")
    })
    //println("at " + stes)
  }
}
