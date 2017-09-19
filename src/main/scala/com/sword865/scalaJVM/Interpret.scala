package com.sword865.scalaJVM

import com.sword865.scalaJVM.instructions.InstructionFactory
import com.sword865.scalaJVM.rtda.{Frame, heap}
import com.sword865.scalaJVM.instructions.base.{BytecodeReader, Instruction}

object Interpret {

  def interpret(method: heap.Method, logInst: Boolean, args: Array[String]=Array[String]()): Unit ={
    val thread = rtda.Thread()
    val frame = thread.newFrame(method)
    thread.pushFrame(frame)

    val jArgs = createArgsArray(method.classStruct.loader, args)
    frame.localVars.setRef(0, jArgs)

    try {
      loop(thread, logInst)
    }catch {
      case e: Throwable =>
        logFrames(thread)
        throw e
    }
  }

  def createArgsArray(loader: heap.ClassLoader, args: Array[String]): heap.Object = {
    val stringClass = loader.loadClass("java/lang/String")
    val argsArr = stringClass.arrayClass().newArray(args.length)
    var jArgs = argsArr.arr[heap.Object]
    for(i<-args.indices) {
      val arg = args.apply(i)
      jArgs(i) = heap.StringPool.JString(loader, arg)
    }
    argsArr
  }

  def loop(thread: rtda.Thread, logInst: Boolean): Unit ={
    val reader = new BytecodeReader()
    var running = true
    while(running){
      try {
        val frame = thread.currentFrame()
        val pc = frame.nextPC
        thread.pc = pc
        //decode
        reader.reset(frame.method.code, pc)
        val opcode = reader.readUInt8()
        val inst = InstructionFactory.newInstruction(opcode)
        inst.fetchOperands(reader)
        frame.nextPC = reader.pc

        if(logInst){
          logInstruction(frame, inst)
        }

        inst.execute(frame)
        if(thread.isStackEmpty){
          running = false
        }
      }catch{
        case e: Throwable =>
          println(e.getMessage)
          throw e
      }
    }
  }

  def logInstruction(frame: Frame, inst: Instruction): Unit = {
    val method = frame.method
    val className = method.classStruct.name
    val methodName = method.name
    val pc = frame.thread.pc
    println(s"$className.$methodName() #$pc inst:$inst")
  }

  def logFrames(thread: rtda.Thread): Unit = {
    if(!thread.isStackEmpty){
      val frame = thread.popFrame()
      val method = frame.method
      val className = method.classStruct.name
      println(s">> pc:${frame.nextPC} $className.${method.name}${method.descriptor}")
    }
  }
}
