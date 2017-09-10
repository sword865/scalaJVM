package com.sword865.scalaJVM.instructions.math

import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction
import com.sword865.scalaJVM.instructions.math.DIV._
import com.sword865.scalaJVM.rtda.Frame

import scala.reflect.ClassTag

object DIV{
  class DDIV extends DIV[Double]{
    override def div(x: Double, y: Double): Double = x / y
  }
  class FDIV extends DIV[Float]{
    override def div(x: Float, y: Float): Float = x / y
  }
  class IDIV extends DIV[Int]{
    override def div(x: Int, y: Int): Int = x / y
  }
  class LDIV extends DIV[Long]{
    override def div(x: Long, y: Long): Long = x / y
  }
}

abstract class DIV[@specialized(Double, Float, Int, Long) T](implicit ev: ClassTag[T])
  extends NoOperandsInstruction{
  def div(x: T, y: T): T

  override def execute(frame: Frame): Unit = {
    val stack = frame.operandStack
    val v2 = stack.pop[T]
    val v1 = stack.pop[T]
    stack.push[T](div(v1, v2))
  }
}
