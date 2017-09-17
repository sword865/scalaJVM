package com.sword865.scalaJVM

import com.sword865.scalaJVM.classpath.ClassPath
import com.sword865.scalaJVM.rtda.heap


object TestClassAndObject {

  def main(args: Array[String]): Unit ={
    val argument = Array[String]("src/test/java/com/sword865/scalaJVM/javafile/MyObject")
    val cmd = Cmd.parseCmd(argument).get
    testClassAndObject(cmd)
  }

  def testClassAndObject(cmd: Cmd): Unit ={
    val cp = ClassPath.parse(cmd.xJreOption, cmd.cpOption)
    val classLoader = heap.ClassLoader(cp)
    val className = cmd.className.replace('.','/')
    val mainClass = classLoader.loadClass(className)
    val mainMethod = mainClass.getMainMethod
    if(mainMethod!=null){
      Interpret.interpret(mainMethod)
    }
  }
}
