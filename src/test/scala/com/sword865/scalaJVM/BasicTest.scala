package com.sword865.scalaJVM

import com.sword865.scalaJVM.Interpret
import com.sword865.scalaJVM.classfile.{ClassFile, MemberInfo}
import com.sword865.scalaJVM.classpath.ClassPath

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

    Interpret.loop(thread, bytecode)
  }

}
