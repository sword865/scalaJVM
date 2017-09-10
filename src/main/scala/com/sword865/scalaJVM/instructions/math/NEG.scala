package com.sword865.scalaJVM.instructions.math

import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction
import com.sword865.scalaJVM.rtda.Frame
import scala.reflect.ClassTag


object NEG{
  class DNEG extends NEG[Double]
  class FNEG extends NEG[Float]
  class INEG extends NEG[Int]
  class LNEG extends NEG[Long]
}


class NEG[@specialized(Double, Float, Int, Long) T](implicit num: Numeric[T], implicit val ev: ClassTag[T])
  extends NoOperandsInstruction{
  override def execute(frame: Frame): Unit = {
    val stack = frame.operandStack
    val value = stack.pop[T]
    stack.push[T](num.negate(value))
  }
}
