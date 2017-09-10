package com.sword865.scalaJVM

import com.sword865.scalaJVM.rtda.{Frame, LocalVars, OperandStack}

object TestOperandStack {
  def main(args: Array[String]): Unit = {
    testOperandStack()
  }

  def testOperandStack(): Unit ={
    val frame = Frame(100, 100)
    testLocalVars(frame.localVars)
    testOperandStack(frame.operandStack)
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

  def testOperandStack(operandStack: OperandStack): Unit = {
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
