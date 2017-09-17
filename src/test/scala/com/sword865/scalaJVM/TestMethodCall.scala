package com.sword865.scalaJVM

import com.sword865.scalaJVM.classpath.ClassPath
import com.sword865.scalaJVM.rtda.heap


object TestMethodCall {

  def main(args: Array[String]): Unit ={
    val argument = Array[String]("-verbose:class", "-verbose:inst",
      "src/test/java/com/sword865/scalaJVM/javafile/InvokeDemo")
    val cmd = Cmd.parseCmd(argument).get
    testClassAndObject(cmd)

    val argument2 = Array[String]("-verbose:class", "-verbose:inst",
      "src/test/java/com/sword865/scalaJVM/javafile/FibonacciTest")
    val cmd2 = Cmd.parseCmd(argument2).get
    testClassAndObject(cmd2)
  }

  def testClassAndObject(cmd: Cmd): Unit ={
    val cp = ClassPath.parse(cmd.xJreOption, cmd.cpOption)
    val classLoader = heap.ClassLoader(cp, cmd.verboseClass)
    val className = cmd.className.replace('.','/')
    val mainClass = classLoader.loadClass(className)
    val mainMethod = mainClass.getMainMethod
    if(mainMethod!=null){
      Interpret.interpret(mainMethod, cmd.verboseInst)
    }
  }
}
