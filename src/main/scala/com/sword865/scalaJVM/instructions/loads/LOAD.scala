package com.sword865.scalaJVM.instructions.loads

import com.sword865.scalaJVM.instructions.base.{Index8Instruction, NoOperandsInstruction}
import com.sword865.scalaJVM.rtda.{Frame, heap}

import scala.reflect.ClassTag

object LOAD{
  class ALOAD extends LOAD[heap.Object]
  class DLOAD extends LOAD[Double]
  class FLOAD extends LOAD[Float]
  class ILOAD extends LOAD[Int]
  class LLOAD extends LOAD[Long]
}

class LOAD[T](implicit val ev: ClassTag[T]) extends Index8Instruction{
  override def execute(frame: Frame): Unit = {
    val value = frame.localVars.get[T](index)
    frame.operandStack.push[T](value)
  }
}
