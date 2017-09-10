package com.sword865.scalaJVM

import com.sword865.scalaJVM.classfile.ClassFile
import com.sword865.scalaJVM.classpath.ClassPath
import com.sword865.scalaJVM.scalaJVM.loadClass

object TestClassFile {

  def main(args: Array[String]): Unit = {
    val argument = Array[String]("java.lang.Object")
    val cmd = Cmd.parseCmd(argument).get
    testClassFile(cmd)
  }

  def testClassFile(cmd: Cmd): Unit ={
    val cp = ClassPath.parse(cmd.xJreOption, cmd.cpOption)
    println("classpath:%s class:%s args:%s".format(cp, cmd.className, cmd.args.mkString(",")))
    val className = cmd.className.replace('.','/')
    val cf = loadClass(className, cp)
    println(cmd.className)
    printClassInfo(cf)
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
}
