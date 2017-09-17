package com.sword865.scalaJVM

import com.sword865.scalaJVM.classfile.MemberInfo
import com.sword865.scalaJVM.instructions.InstructionFactory
import com.sword865.scalaJVM.rtda.{Frame, heap}
import com.sword865.scalaJVM.instructions.base.{BytecodeReader, Instruction}

object Interpret {

  def interpret(method: heap.Method, logInst: Boolean): Unit ={
    val thread = rtda.Thread()
    val frame = thread.newFrame(method)
    thread.pushFrame(frame)
    try {
      loop(thread, logInst)
    }catch {
      case _: Throwable => logFrames(thread)
    }
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

  def logInstruction(frame: Frame, inst: Instruction) = {
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
