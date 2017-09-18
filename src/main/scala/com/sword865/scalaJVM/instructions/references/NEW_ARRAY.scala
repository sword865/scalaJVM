package com.sword865.scalaJVM.instructions.references

import com.sword865.scalaJVM.instructions.base.{BytecodeReader, Instruction}
import com.sword865.scalaJVM.rtda.heap.ClassStruct
import com.sword865.scalaJVM.rtda.{Frame, heap}

/**
  * Created by tianhaowei on 2017/9/18.
  */

object NEW_ARRAY{
  val AT_BOOLEAN = 4
  val AT_CHAR    = 5
  val AT_FLOAT   = 6
  val AT_DOUBLE  = 7
  val AT_BYTE    = 8
  val AT_SHORT   = 9
  val AT_INT     = 10
  val AT_LONG    = 11
}

class NEW_ARRAY extends Instruction{

  var atype: Byte = 0

  override def toString: String = s"NEW_ARRAY(atype=$atype)"

  override def fetchOperands(reader: BytecodeReader): Unit = {
    atype = reader.readInt8()
  }

  override def execute(frame: Frame): Unit = {
    val stack = frame.operandStack
    val count = stack.popInt()
    if(count < 0){
      throw new Exception("java.lang.NegativeArraySizeException")
    }
    val classLoader = frame.method.classStruct.loader
    val arrClass = getPrimitiveArrayClass(classLoader, atype)
    val arr =arrClass.newArray(count)
    stack.pushRef(arr)
  }

  def getPrimitiveArrayClass(loader: heap.ClassLoader, atype: Byte): ClassStruct = {
    import NEW_ARRAY._
    atype match{
      case AT_BOOLEAN =>
        loader.loadClass("[Z")
      case AT_BYTE =>
        loader.loadClass("[B")
      case AT_CHAR =>
        loader.loadClass("[C")
      case AT_SHORT =>
        loader.loadClass("[S")
      case AT_INT =>
        loader.loadClass("[I")
      case AT_LONG =>
        loader.loadClass("[J")
      case AT_FLOAT =>
        loader.loadClass("[F")
      case AT_DOUBLE =>
        loader.loadClass("[D")
      case _ =>
        throw new Exception("Invalid atype")
    }
  }
}
