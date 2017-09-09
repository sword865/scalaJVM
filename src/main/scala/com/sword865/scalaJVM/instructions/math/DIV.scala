package com.sword865.scalaJVM.instructions.math

import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction
import com.sword865.scalaJVM.instructions.math.DIV._
import com.sword865.scalaJVM.rtda.Frame

import scala.reflect.ClassTag

object DIV{
  type DDIV = DIV[Double]
  type FDIV = DIV[Float]
  type IDIV = DIV[Int]
  type LDIV = DIV[Long]

  trait DIVNumeric[T] {
    def div(x: T, y: T): T
  }
  class Intdiv extends DIVNumeric[Int]{
    override def div(x: Int, y: Int): Int = x / y
  }
  implicit val intdiv = new Intdiv

  class Floatdiv extends DIVNumeric[Float]{
    override def div(x: Float, y: Float): Float = x / y
  }
  implicit val floatdiv = new Floatdiv

  class Doublediv extends DIVNumeric[Double]{
    override def div(x: Double, y: Double): Double = x / y
  }
  implicit val doublediv = new Doublediv

  class Longdiv extends DIVNumeric[Long]{
    override def div(x: Long, y: Long): Long = x / y
  }
  implicit val longdiv = new Longdiv
}

class DIV[@specialized(Double, Float, Int, Long) T](implicit num: DIVNumeric[T], implicit val ev: ClassTag[T])
  extends NoOperandsInstruction{
  override def execute(frame: Frame): Unit = {
    val stack = frame.operandStack
    val v2 = stack.pop[T]
    val v1 = stack.pop[T]
    stack.push[T](num.div(v1, v2))
  }
}
