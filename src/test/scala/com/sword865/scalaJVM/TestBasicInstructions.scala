package com.sword865.scalaJVM

import com.sword865.scalaJVM.classpath.ClassPath
import com.sword865.scalaJVM.scalaJVM.{getMainMethod, loadClass}


object TestBasicInstructions {

  def main(args: Array[String]): Unit ={
    val argument = Array[String]("src/test/java/com/sword865/scalaJVM/javafile/GaussTest")
    val cmd = Cmd.parseCmd(argument).get
    testBasicInstructions(cmd)
  }


  def testBasicInstructions(cmd: Cmd): Unit ={
    val cp = ClassPath.parse(cmd.xJreOption, cmd.cpOption)
    val className = cmd.className.replace('.','/')
    val cf = loadClass(className, cp)
    println(f"start: ${cf.className}")
    val mainMethod = getMainMethod(cf)
    mainMethod.foreach(x=> {
      println("start main")
      Interpret.interpret(x)
    })
  }


}
