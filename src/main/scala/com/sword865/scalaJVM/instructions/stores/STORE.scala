package com.sword865.scalaJVM.instructions.stores

import com.sword865.scalaJVM.instructions.base.Index8Instruction
import com.sword865.scalaJVM.rtda.{Frame, heap}

import scala.reflect.ClassTag

object STORE{
  class ASTORE extends STORE[heap.Object]
  class DSTORE extends STORE[Double]
  class FSTORE extends STORE[Float]
  class ISTORE extends STORE[Int]
  class LSTORE extends STORE[Long]
}

class STORE[T](implicit val ev: ClassTag[T]) extends Index8Instruction{
  override def execute(frame: Frame): Unit = {
    val value = frame.operandStack.pop[T]
    frame.localVars.set[T](index, value)
  }
}
