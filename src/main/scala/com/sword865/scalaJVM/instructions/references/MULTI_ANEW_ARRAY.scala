package com.sword865.scalaJVM.instructions.references

import com.sword865.scalaJVM.instructions.base.{BytecodeReader, Instruction}
import com.sword865.scalaJVM.rtda.heap.ClassStruct
import com.sword865.scalaJVM.rtda.{Frame, OperandStack, heap}

/**
  * Created by tianhaowei on 2017/9/18.
  */
class MULTI_ANEW_ARRAY extends Instruction{

  var index: Int = 0
  var dimensions: Short = 0

  override def toString: String = s"MULTI_ANEW_ARRAY(index=$index, dimensions=$dimensions)"

  override def fetchOperands(reader: BytecodeReader): Unit = {
    index = reader.readUInt16()
    dimensions = reader.readUInt8()
  }

  override def execute(frame: Frame): Unit = {
    val cp = frame.method.classStruct.constantPool
    val classRef = cp.getConstant(index).asInstanceOf[heap.ClassRef]
    val arrClass = classRef.resolvedClass()

    val stack = frame.operandStack
    val counts = popAndCheckCount(stack, dimensions)
    val arr = newMultiDimensionalArray(counts, arrClass)
    stack.pushRef(arr)
  }

  def popAndCheckCount(stack: OperandStack, dimensions: Short): Array[Int] = {
    val counts = (0 until dimensions).map(_ => stack.popInt()).reverse
    if(counts.min < 0){
      throw new Exception("java.lang.NegativeArraySizeException")
    }
    counts.toArray
  }

  def newMultiDimensionalArray(counts: Array[Int], arrClass: ClassStruct): heap.Object = {
    val count = counts(0)
    val arr = arrClass.newArray(count)
    if(counts.length > 1){
      val refs = arr.arr[heap.Object]
      for(i <- refs.indices){
        refs(i) = newMultiDimensionalArray(counts.drop(1), arrClass.componentClass())
      }

    }
    arr
  }
}
