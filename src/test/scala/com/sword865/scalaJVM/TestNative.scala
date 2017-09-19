package com.sword865.scalaJVM

import com.sword865.scalaJVM.classpath.ClassPath
import com.sword865.scalaJVM.rtda.heap


object TestNative {

  def main(args: Array[String]): Unit ={
    val argument = Array[String]( // "-verbose:class", "-verbose:inst",
      "src/test/java/com/sword865/scalaJVM/javafile/GetClassTest")
    val cmd = Cmd.parseCmd(argument).get
    //testClassAndObject(cmd)

    val argument2 = Array[String]( //"-verbose:class", "-verbose:inst",
      "src/test/java/com/sword865/scalaJVM/javafile/StringTest")
    val cmd2 = Cmd.parseCmd(argument2).get
    //testClassAndObject(cmd2)

    val argument3 = Array[String]( //"-verbose:class", "-verbose:inst",
      "src/test/java/com/sword865/scalaJVM/javafile/CloneTest")
    val cmd3 = Cmd.parseCmd(argument3).get
    //testClassAndObject(cmd3)
//
//    val argument4 = Array[String](//"-verbose:class", "-verbose:inst",
//      "src/test/java/com/sword865/scalaJVM/javafile/BoxTest")
//    val cmd4 = Cmd.parseCmd(argument4).get
//    testClassAndObject(cmd4)

  }

  def testClassAndObject(cmd: Cmd): Unit ={
    val cp = ClassPath.parse(cmd.xJreOption, cmd.cpOption)
    val classLoader = heap.ClassLoader(cp, cmd.verboseClass)
    val className = cmd.className.replace('.','/')
    val mainClass = classLoader.loadClass(className)
    val mainMethod = mainClass.getMainMethod
    if(mainMethod!=null){
      Interpret.interpret(mainMethod, cmd.verboseInst, cmd.args)
    }
  }
}
