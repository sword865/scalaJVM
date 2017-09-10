package com.sword865.scalaJVM

import com.sword865.scalaJVM.classfile.MemberInfo
import com.sword865.scalaJVM.instructions.InstructionFactory
import com.sword865.scalaJVM.rtda.Frame
import com.sword865.scalaJVM.instructions.base.BytecodeReader

object Interpret {
  def interpret(methodInfo: MemberInfo): Unit ={
    val codeAttr = methodInfo.codeAttribute
    val maxLocals = codeAttr.maxLocals
    val maxStack = codeAttr.maxStack
    val bytecode = codeAttr.code

    val frame = Frame(maxLocals, maxStack)
    val thread = rtda.Thread()
    thread.pushFrame(frame)

    loop(thread, bytecode)
  }

  def loop(thread: rtda.Thread, bytecode: Array[Byte]): Unit ={
    val frame = thread.popFrame()
    val reader = new BytecodeReader()
    while(true){
      val pc = frame.nextPC
      thread.pc = pc

      reader.reset(bytecode, pc)
      val opcode = reader.readUInt8()
      val inst = InstructionFactory.newInstruction(opcode)
      inst.fetchOperands(reader)
      frame.nextPC = reader.pc

      println(f"pc:$pc inst:${inst.toString}")
      inst.execute(frame)
    }
  }

}
