package com.sword865.scalaJVM.instructions.stores

import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction
import com.sword865.scalaJVM.rtda.{Frame, heap}

import scala.reflect.ClassTag

/**
  * Created by tianhaowei on 2017/9/18.
  */

object XASTORE{

  class AASTORE extends XASTORE[heap.Object]
  class BASTORE extends XASTORE[Byte]
  class CASTORE extends XASTORE[Char]
  class FASTORE extends XASTORE[Float]
  class DASTORE extends XASTORE[Double]
  class IASTORE extends XASTORE[Int]
  class LASTORE extends XASTORE[Long]
  class SASTORE extends XASTORE[Short]
}

class XASTORE[T](implicit val ev: ClassTag[T]) extends NoOperandsInstruction{
  override def execute(frame: Frame): Unit = {
    val stack = frame.operandStack
    val ref = stack.pop[T]
    val index = stack.popInt()

    val arrRef = stack.popRef()
    if(arrRef==null){
      throw new Exception("java.lang.NullPointerException")
    }

    val refs = arrRef.arr[T]
    val arrLen = refs.length
    if(index<0 || index> arrLen){
      throw new Exception("ArrayIndexOutOfBoundsException")
    }

    refs(index) = ref

  }
}
