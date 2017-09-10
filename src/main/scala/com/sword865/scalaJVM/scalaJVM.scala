package com.sword865.scalaJVM

import com.sword865.scalaJVM.classfile.{ClassFile, MemberInfo}
import com.sword865.scalaJVM.classpath.ClassPath

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

  def startJVM(): Unit = {
    //testOperandStack()
  }

  def getMainMethod(cf: ClassFile): Option[MemberInfo] ={
    cf.methods.find(m=>
      m.name == "main" && m.descriptor == "([Ljava/lang/String;)V"
    )
  }
}
