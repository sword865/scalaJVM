package com.sword865.scalaJVM.instructions.base

import com.sword865.scalaJVM.rtda

import scala.reflect.ClassTag

object Instruction{
  def branch(frame: rtda.Frame, offset: Int): Unit = {
    frame.nextPC =  frame.thread.pc + offset
  }
}

abstract class Instruction {
  def fetchOperands(reader: BytecodeReader)
  def execute(frame: rtda.Frame)

  //override def toString: String = {
    //val classType = getClass
    //val fields = classType.getDeclaredFields
    //fields.foreach(_.setAccessible(true))
    //val filedstr= fields.map(x=>f"${x.getName}, ${x.get(this)}").mkString(",")
    //f"${classType.getSimpleName}:($filedstr)"
  //}
}

object NoOperandsInstruction {
  class VALUE
  class ZERO extends VALUE
  class MONE extends VALUE
  class ONE extends VALUE
  class TWO extends VALUE
  class THREE extends VALUE
  class FOUR extends VALUE
  class FIVE extends VALUE

  def convertClassToValue[V <: VALUE](vev: ClassTag[V]): Short ={
    if(vev == manifest[ZERO]){
      0
    }else if(vev == manifest[MONE]) {
      -1
    }else if(vev == manifest[ONE]){
      1
    }else if(vev == manifest[TWO]){
      2
    }else if(vev == manifest[THREE]){
      3
    }else if(vev == manifest[FOUR]){
      4
    }else if(vev == manifest[FIVE]){
      5
    }else{
      throw new Exception("unknow value for CONST OP")
    }
  }
}

abstract class NoOperandsInstruction extends Instruction{
  override def fetchOperands(reader: BytecodeReader): Unit = {}

  override def toString: String = s"${this.getClass.getSimpleName}()"
}

abstract class BranchInstruction extends Instruction{
  var offset: Int = 0
  override def fetchOperands(reader: BytecodeReader): Unit = {
    offset = reader.readInt16()
  }
  override def toString: String = s"${this.getClass.getSimpleName}(offset=$offset)"
}

abstract class Index8Instruction extends Instruction{
  var index: Int = 0
  override def fetchOperands(reader: BytecodeReader): Unit = {
    index = reader.readUInt8().toInt
  }
  override def toString: String = s"${this.getClass.getSimpleName}(index=$index)"
}

abstract class Index16Instruction extends Instruction{
  var index: Int = 0
  override def fetchOperands(reader: BytecodeReader): Unit = {
    index = reader.readUInt16()
  }
  override def toString: String = s"${this.getClass.getSimpleName}(index=$index)"
}