package com.sword865.scalaJVM

import com.sword865.scalaJVM.classfile.MemberInfo
import com.sword865.scalaJVM.instructions.InstructionFactory
import com.sword865.scalaJVM.rtda.{Frame, heap}
import com.sword865.scalaJVM.instructions.base.BytecodeReader

object Interpret {

  def interpret(method: heap.Method): Unit ={
    val thread = rtda.Thread()
    val frame = thread.newFrame(method)
    thread.pushFrame(frame)
    loop(thread, method.code)
  }

  def loop(thread: rtda.Thread, bytecode: Array[Byte]): Unit ={
    val frame = thread.popFrame()
    val reader = new BytecodeReader()
    while(true){
      try {

        val pc = frame.nextPC
        thread.pc = pc

        reader.reset(bytecode, pc)
        val opcode = reader.readUInt8()
        val inst = InstructionFactory.newInstruction(opcode)
        inst.fetchOperands(reader)
        frame.nextPC = reader.pc

        println(f"pc:$pc inst:${inst.toString}")
        inst.execute(frame)
      }catch{
        case e: Throwable =>
          println(e.getMessage)
          println(frame.localVars)
          println(frame.operandStack)
          throw e
      }
    }
  }

}
