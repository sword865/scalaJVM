package com.sword865.scalaJVM.instructions.loads

import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction
import com.sword865.scalaJVM.rtda.Frame
import com.sword865.scalaJVM.rtda.heap

import scala.reflect.ClassTag
/**
  * Created by tianhaowei on 2017/9/18.
  */
object XALOAD{

  class AALOAD extends XALOAD[heap.Object]
  class BALOAD extends XALOAD[Byte]
  class CALOAD extends XALOAD[Char]
  class FALOAD extends XALOAD[Float]
  class DALOAD extends XALOAD[Double]
  class IALOAD extends XALOAD[Int]
  class LALOAD extends XALOAD[Long]
  class SALOAD extends XALOAD[Short]
}


class XALOAD[T](implicit val ev: ClassTag[T]) extends NoOperandsInstruction{
  override def execute(frame: Frame): Unit = {
    val stack = frame.operandStack
    val index = stack.popInt()
    val arrRef = stack.popRef()

    if(arrRef==null){
      throw new Exception("java.lang.NullPointerException")
    }

    val refs = arrRef.arr[T]
    val arrLen = refs.length
    if(index<0 || index>arrLen){
      throw new Exception("ArrayIndexOutOfBoundsException")
    }

    stack.push[T](refs(index))
  }
}
