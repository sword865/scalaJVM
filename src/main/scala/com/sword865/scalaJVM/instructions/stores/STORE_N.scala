package com.sword865.scalaJVM.instructions.stores

import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction
import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction._
import com.sword865.scalaJVM.rtda.Frame

import scala.reflect.ClassTag

object STORE_N{
  type ASTORE_N[V <: VALUE] = STORE_N[AnyRef, V]
  type DSTORE_N[V <: VALUE] = STORE_N[Double, V]
  type FSTORE_N[V <: VALUE] = STORE_N[Float, V]
  type ISTORE_N[V <: VALUE] = STORE_N[Int, V]
  type LSTORE_N[V <: VALUE] = STORE_N[Long, V]

  type ASTORE_0 = ASTORE_N[ZERO]
  type ASTORE_1 = ASTORE_N[ONE]
  type ASTORE_2 = ASTORE_N[TWO]
  type ASTORE_3 = ASTORE_N[THREE]
  type DSTORE_0 = DSTORE_N[ZERO]
  type DSTORE_1 = DSTORE_N[ONE]
  type DSTORE_2 = DSTORE_N[TWO]
  type DSTORE_3 = DSTORE_N[THREE]
  type FSTORE_0 = FSTORE_N[ZERO]
  type FSTORE_1 = FSTORE_N[ONE]
  type FSTORE_2 = FSTORE_N[TWO]
  type FSTORE_3 = FSTORE_N[THREE]
  type ISTORE_0 = ISTORE_N[ZERO]
  type ISTORE_1 = ISTORE_N[ONE]
  type ISTORE_2 = ISTORE_N[TWO]
  type ISTORE_3 = ISTORE_N[THREE]
  type LSTORE_0 = LSTORE_N[ZERO]
  type LSTORE_1 = LSTORE_N[ONE]
  type LSTORE_2 = LSTORE_N[TWO]
  type LSTORE_3 = LSTORE_N[THREE]
}

class STORE_N[T, V <: VALUE](implicit val ev: ClassTag[T], implicit val vev: ClassTag[V])
  extends NoOperandsInstruction {

  val index: Short = convertClassToValue(vev)

  override def execute(frame: Frame): Unit = {
    val value = frame.operandStack.pop[T]
    frame.localVars.set[T](index, value)
  }

}
