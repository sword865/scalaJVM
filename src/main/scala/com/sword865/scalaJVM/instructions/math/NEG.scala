package com.sword865.scalaJVM.instructions.math

import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction
import com.sword865.scalaJVM.rtda.Frame
import scala.reflect.ClassTag


object NEG{
  type DNEG = NEG[Double]
  type FNEG = NEG[Float]
  type INEG = NEG[Int]
  type LNEG = NEG[Long]
}


class NEG[@specialized(Double, Float, Int, Long) T](implicit num: Numeric[T], implicit val ev: ClassTag[T])
  extends NoOperandsInstruction{
  override def execute(frame: Frame): Unit = {
    val stack = frame.operandStack
    val value = stack.pop[T]
    stack.push[T](num.negate(value))
  }
}
