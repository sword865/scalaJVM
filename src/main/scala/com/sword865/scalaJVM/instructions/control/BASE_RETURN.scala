package com.sword865.scalaJVM.instructions.control

import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction
import com.sword865.scalaJVM.rtda.{Frame, heap}

import scala.reflect.ClassTag
object BASE_RETURN {
  class RETURN extends NoOperandsInstruction{
    override def execute(frame: Frame): Unit = {
      frame.thread.popFrame()
    }
  }

  class ARETURN extends BASE_RETURN[heap.Object]
  class DRETURN extends BASE_RETURN[Double]
  class FRETURN extends BASE_RETURN[Float]
  class IRETURN extends BASE_RETURN[Int]
  class LRETURN extends BASE_RETURN[Long]

}

class BASE_RETURN[T](implicit val ev: ClassTag[T]) extends NoOperandsInstruction{
  override def execute(frame: Frame): Unit = {
    val thread = frame.thread
    val currentFrame = thread.popFrame()
    val invokerFrame = thread.topFrame()
    val value: T = currentFrame.operandStack.pop[T]
    invokerFrame.operandStack.push[T](value)
  }
}

