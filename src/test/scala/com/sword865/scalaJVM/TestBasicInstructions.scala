package com.sword865.scalaJVM

import com.sword865.scalaJVM.classpath.ClassPath


object TestBasicInstructions {

  def main(args: Array[String]): Unit ={
    val argument = Array[String]("src/test/java/com/sword865/scalaJVM/javafile/GaussTest")
    val cmd = Cmd.parseCmd(argument).get
    testBasicInstructions(cmd)
  }


  def testBasicInstructions(cmd: Cmd): Unit ={
    val cp = ClassPath.parse(cmd.xJreOption, cmd.cpOption)
    val className = cmd.className.replace('.','/')
    val cf = BasicTest.loadClass(className, cp)
    println(f"start: ${cf.className}")
    val mainMethod = BasicTest.getMainMethod(cf)
    mainMethod.foreach(x=> {
      println("start main")
      BasicTest.interpret(x)
    })
  }


}
