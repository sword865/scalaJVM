package com.sword865.scalaJVM.instructions.math
import com.sword865.scalaJVM.instructions.math.REM._

import com.sword865.scalaJVM.instructions.base.Index8Instruction
import com.sword865.scalaJVM.rtda.Frame

import scala.reflect.ClassTag
object REM {

  type DREM = REM[Double]
  type FREM = REM[Float]
  type IREM = REM[Int]
  type LREM = REM[Long]

  trait RemNumeric[T] {
    def zero: T
    def rem(x: T, y: T): T
  }
  class IntRem extends RemNumeric[Int]{
    override def zero: Int = 0
    override def rem(x: Int, y: Int): Int = x % y
  }
  implicit val intRem = new IntRem

  class FloatRem extends RemNumeric[Float]{
    override def zero: Float = 0.0F
    override def rem(x: Float, y: Float): Float = x % y
  }
  implicit val floatRem = new FloatRem

  class DoubleRem extends RemNumeric[Double]{
    override def zero: Double = 0.0
    override def rem(x: Double, y: Double): Double = x % y
  }
  implicit val doubleRem = new DoubleRem

  class LongRem extends RemNumeric[Long]{
    override def zero: Long = 0L
    override def rem(x: Long, y: Long): Long = x % y
  }
  implicit val longRem = new LongRem

}

class REM[@specialized(Double, Float, Int, Long) T](implicit num: RemNumeric[T], implicit val ev: ClassTag[T])
  extends Index8Instruction{
  override def execute(frame: Frame): Unit = {
    val stack = frame.operandStack
    val v2 = stack.pop[T]
    val v1 = stack.pop[T]
    if(v2 == num.zero){
      throw new Exception("rem on zero value")
    }
    num.rem(v1, v2)
  }
}
