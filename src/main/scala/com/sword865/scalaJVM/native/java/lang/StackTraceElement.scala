package com.sword865.scalaJVM.native.java.lang

import com.sword865.scalaJVM.native
import com.sword865.scalaJVM.rtda.{Frame, heap}
import com.sword865.scalaJVM.rtda.heap.ClassStruct

/**
  * Created by tianhaowei on 2017/9/19.
  */
import com.sword865.scalaJVM.{native, rtda}

object StackTraceElement{

  val jlThrowable = "java/lang/Throwable"

  def init(): Unit ={
    native.register(jlThrowable, "fillInStackTrace", "(I)Ljava/lang/Throwable;", fillInStackTrace)
  }

  def fillInStackTrace(frame: rtda.Frame): Unit = {
    val thisClass = frame.localVars.getThis
    frame.operandStack.pushRef(thisClass)

    val stes = createStackTraceElements(thisClass, frame.thread)
    thisClass.extra = stes
  }

  def distanceToObject(classStruct: ClassStruct): Int = {
    var distance = 0
    var c = classStruct.superClass
    while(c!=null){
      c = c.superClass
      distance += 1
    }
    distance
  }

  def createStackTraceElement(frame: Frame): StackTraceElement = {
    val method = frame.method
    val classStruct = method.classStruct
    new StackTraceElement(classStruct.sourceFile, classStruct.javaName, method.name, method.getLineNumber(frame.nextPC-1))
  }

  def createStackTraceElements(tObj: heap.Object, thread: rtda.Thread): Array[StackTraceElement] = {
    val skip = distanceToObject(tObj.classStruct) + 2
    val frames = thread.getFrames.drop(skip)
    frames.map(frame=>{
      createStackTraceElement(frame)
    })
  }

}

case class StackTraceElement(fileName: String, className: String, methodName: String, lineNumber: Int)
