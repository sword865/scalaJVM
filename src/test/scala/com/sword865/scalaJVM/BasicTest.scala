package com.sword865.scalaJVM

import com.sword865.scalaJVM.Interpret
import com.sword865.scalaJVM.classfile.{ClassFile, MemberInfo}
import com.sword865.scalaJVM.classpath.ClassPath
import com.sword865.scalaJVM.instructions.InstructionFactory
import com.sword865.scalaJVM.instructions.base.BytecodeReader

object BasicTest {
 def loadClass(className: String, cp: ClassPath): ClassFile ={
  val readResult = cp.readClass(className)
  val classData = if(readResult.nonEmpty){
   readResult.get._2
  } else{
   throw new Exception("class file not found exceptions")
  }
  ClassFile.parse(classData)
 }

 def getMainMethod(cf: ClassFile): Option[MemberInfo] ={
  cf.methods.find(m=>
   m.name == "main" && m.descriptor == "([Ljava/lang/String;)V"
  )
 }

  def interpret(methodInfo: MemberInfo): Unit ={
    val codeAttr = methodInfo.codeAttribute
    val maxLocals = codeAttr.maxLocals
    val maxStack = codeAttr.maxStack
    val bytecode = codeAttr.code

    val thread = rtda.Thread()
    val frame = thread.newFrame(maxLocals, maxStack)
    thread.pushFrame(frame)

    loop(thread, bytecode)
  }


  def loop(thread: rtda.Thread, bytecode: Array[Byte]): Unit ={
    val frame = thread.popFrame()
    val reader = new BytecodeReader()
    while(true){
      try {
        val pc = frame.nextPC
        thread.pc = pc

        //decode
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
