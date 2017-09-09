package com.sword865.scalaJVM.instructions.loads

import com.sword865.scalaJVM.instructions.base.{Index8Instruction, NoOperandsInstruction}
import com.sword865.scalaJVM.rtda.Frame
import scala.reflect.ClassTag

object LOAD{
  type ALOAD = LOAD[AnyRef]
  type DLOAD = LOAD[Double]
  type FLOAD = LOAD[Float]
  type ILOAD = LOAD[Int]
  type LLOAD = LOAD[Long]
}

class LOAD[T](implicit val ev: ClassTag[T]) extends Index8Instruction{
  override def execute(frame: Frame): Unit = {
    val value = frame.localVars.get[T](index)
    frame.operandStack.push[T](value)
  }
}
