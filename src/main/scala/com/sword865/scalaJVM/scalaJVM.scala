package com.sword865.scalaJVM

import com.sword865.scalaJVM.classfile.ClassFile
import com.sword865.scalaJVM.classpath.ClassPath
import com.sword865.scalaJVM.rtda.{Frame, LocalVars, OperandStack}

/**
  * Created by tianhaowei on 2017/9/5.
  */


object scalaJVM extends App{

  println("start jvm with args: " + args.mkString(" "))
  val argument = if(args.isEmpty){
    Array[String]("java.lang.Object")
  }else{
    args
  }

  val cmd = Cmd.parseCmd(argument).get
  if(cmd.version){
    println("version 0.0.1")
  }else if(cmd.help || cmd.className.isEmpty){
    Cmd.printUsage()
  }else{
    startJVM()
  }

  def loadClass(className: String, cp: ClassPath): ClassFile ={
    val readResult = cp.readClass(className)
    val classData = if(readResult.nonEmpty){
      readResult.get._2
    } else{
      throw new Exception("class file not found exceptions")
    }
    ClassFile.parse(classData)
  }

  def printClassInfo(cf: ClassFile): Unit ={
    println(f"version: ${cf.majorVersion}.${cf.minorVersion}")
    println(f"constants count: ${cf.constantPool.pool.length}")
    println(f"access flags: 0x${cf.accessFlags.toHexString}")
    println(f"this class: ${cf.className}")
    println(f"super class: ${cf.superClass}")
    println(f"interfaces: ${cf.interfaceNames().toList}")
    println(f"fields count: ${cf.fields.length}")
    cf.fields.foreach(f => println(f"  ${f.name}"))
    println(f"methods count: ${cf.methods.length}")
    cf.methods.foreach(m => println(f"  ${m.name}"))
  }

  def startJVM(): Unit = {
    testOperandStack()
  }

  def testOperandStack(): Unit ={
    val frame = Frame(100, 100)
    testLocalVars(frame.localVars)
    testIoerandStack(frame.operandStack)
  }



  def TestClassFile(): Unit ={
    val cp = ClassPath.parse(cmd.xJreOption, cmd.cpOption)
    println("classpath:%s class:%s args:%s".format(cp, cmd.className, cmd.args.mkString(",")))
    val className = cmd.className.replace('.','/')
    val cf = loadClass(className, cp)
    println(cmd.className)
    printClassInfo(cf)
  }

  def testLocalVars(localVars: LocalVars): Unit = {
    localVars.setInt(0, 100)
    localVars.setInt(1, -100)
    localVars.setLong(2, 2997924580L)
    localVars.setLong(4, -2997924580L)
    localVars.setFloat(6, 3.1415926F)
    localVars.setFloat(7, -3.1415926F)
    localVars.setDouble(8, 2.71828182845)
    localVars.setDouble(10, -2.71828182845)
    localVars.setRef(12, null)
    println(localVars.getInt(0))
    println(localVars.getInt(1))
    println(localVars.getLong(2))
    println(localVars.getLong(4))
    println(localVars.getFloat(6))
    println(localVars.getFloat(7))
    println(localVars.getDouble(8))
    println(localVars.getDouble(10))
    println(localVars.getRef(12))
  }

  def testIoerandStack(operandStack: OperandStack): Unit = {
    operandStack.pushInt(100)
    operandStack.pushInt(-100)
    operandStack.pushLong(2997924580L)
    operandStack.pushLong(-2997924580L)
    operandStack.pushFloat(3.1415926F)
    operandStack.pushFloat(-3.1415926F)
    operandStack.pushDouble(2.71828182845)
    operandStack.pushDouble(-2.71828182845)
    operandStack.pushRef(null)
    println(operandStack.popRef())
    println(operandStack.popDouble())
    println(operandStack.popDouble())
    println(operandStack.popFloat())
    println(operandStack.popFloat())
    println(operandStack.popLong())
    println(operandStack.popLong())
    println(operandStack.popInt())
    println(operandStack.popInt())
  }


}
