package com.sword865.scalaJVM.instructions

import com.sword865.scalaJVM.rtda
import com.sword865.scalaJVM.rtda.heap
import com.sword865.scalaJVM.rtda.heap.ClassStruct

package object base {

  def invokeMethod(invokerFrame: rtda.Frame, method: heap.Method): Unit ={
    val thread = invokerFrame.thread
    val newFrame = thread.newFrame(method)
    thread.pushFrame(newFrame)
    val argSlotCount = method.argSlotCount
    if(argSlotCount>0){
      (0 until argSlotCount).reverse.foreach(i=> {
        val slot = invokerFrame.operandStack.popSlot()
        newFrame.localVars.setSlot(i, slot)
      })
    }
    //hacker
    if(method.isNative){
      if(method.name=="registerNatives" ){
        thread.popFrame()
      }else{
        throw new Exception(s"native method: ${method.classStruct.name}.${method.name}${method.descriptor}")
      }
    }
  }

  def scheduleClinit(thread: rtda.Thread, classStruct: ClassStruct):Unit = {
    val clinit = classStruct.getClinitMethod
    if(clinit!=null){
      val newFrame = thread.newFrame(clinit)
      thread.pushFrame(newFrame)
    }
  }

  def initSuperClass(thread: rtda.Thread, classStruct: ClassStruct): Unit = {
    if(!classStruct.isInterface){
      val superClass = classStruct.superClass
      if(superClass!=null && !superClass.initStarted){
        initClass(thread, superClass)
      }
    }
  }

  def initClass(thread: rtda.Thread, classStruct: heap.ClassStruct): Unit ={
    classStruct.startInit()
    scheduleClinit(thread, classStruct)
    initSuperClass(thread, classStruct)
  }

}
