package com.sword865.scalaJVM.instructions.loads

import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction
import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction._
import com.sword865.scalaJVM.rtda.Frame

import scala.reflect.ClassTag

object LOAD_N{
  type ALOAD_N[V <: VALUE] = LOAD_N[AnyRef, V]
  type DLOAD_N[V <: VALUE] = LOAD_N[Double, V]
  type FLOAD_N[V <: VALUE] = LOAD_N[Float, V]
  type ILOAD_N[V <: VALUE] = LOAD_N[Int, V]
  type LLOAD_N[V <: VALUE] = LOAD_N[Long, V]

  type ALOAD_0 = ALOAD_N[ZERO]
  type ALOAD_1 = ALOAD_N[ONE]
  type ALOAD_2 = ALOAD_N[TWO]
  type ALOAD_3 = ALOAD_N[THREE]
  type DLOAD_0 = DLOAD_N[ZERO]
  type DLOAD_1 = DLOAD_N[ONE]
  type DLOAD_2 = DLOAD_N[TWO]
  type DLOAD_3 = DLOAD_N[THREE]
  type FLOAD_0 = FLOAD_N[ZERO]
  type FLOAD_1 = FLOAD_N[ONE]
  type FLOAD_2 = FLOAD_N[TWO]
  type FLOAD_3 = FLOAD_N[THREE]
  type ILOAD_0 = ILOAD_N[ZERO]
  type ILOAD_1 = ILOAD_N[ONE]
  type ILOAD_2 = ILOAD_N[TWO]
  type ILOAD_3 = ILOAD_N[THREE]
  type LLOAD_0 = LLOAD_N[ZERO]
  type LLOAD_1 = LLOAD_N[ONE]
  type LLOAD_2 = LLOAD_N[TWO]
  type LLOAD_3 = LLOAD_N[THREE]
}


class LOAD_N[T, V <: VALUE](implicit val ev: ClassTag[T], implicit val vev: ClassTag[V])
  extends NoOperandsInstruction {

  val index: Short = convertClassToValue(vev)

  override def execute(frame: Frame): Unit = {
    val value = frame.localVars.get[T](index)
    frame.operandStack.push[T](value)
  }
}
