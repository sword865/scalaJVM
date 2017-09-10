package com.sword865.scalaJVM.instructions.math

import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction
import com.sword865.scalaJVM.rtda.Frame

import scala.reflect.ClassTag

object SUB{
  class DSUB extends SUB[Double]
  class FSUB extends SUB[Float]
  class ISUB extends SUB[Int]
  class LSUB extends SUB[Long]
}

class SUB[@specialized(Double, Float, Int, Long) T](implicit num: Numeric[T], implicit val ev: ClassTag[T])
  extends NoOperandsInstruction{
  override def execute(frame: Frame): Unit = {
    val stack = frame.operandStack
    val v2 = stack.pop[T]
    val v1 = stack.pop[T]
    stack.push[T](num.minus(v1, v2))
  }
}
