package com.sword865.scalaJVM.instructions.stores

import com.sword865.scalaJVM.instructions.base.Index8Instruction
import com.sword865.scalaJVM.rtda.Frame

import scala.reflect.ClassTag

object STORE{
  type ASTORE = STORE[AnyRef]
  type DSTORE = STORE[Double]
  type FSTORE = STORE[Float]
  type ISTORE = STORE[Int]
  type LSTORE = STORE[Long]
}

class STORE[T](implicit val ev: ClassTag[T]) extends Index8Instruction{
  override def execute(frame: Frame): Unit = {
    val value = frame.operandStack.pop[T]
    frame.localVars.set[T](index, value)
  }
}
