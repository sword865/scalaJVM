package com.sword865.scalaJVM.instructions.math
import com.sword865.scalaJVM.instructions.math.REM._

import com.sword865.scalaJVM.instructions.base.Index8Instruction
import com.sword865.scalaJVM.rtda.Frame

import scala.reflect.ClassTag
object REM {

  class DREM extends REM[Double]{
    override def zero: Double = 0.0
    override def rem(x: Double, y: Double): Double = x % y
  }
  class FREM extends REM[Float]{
    override def zero: Float = 0.0F
    override def rem(x: Float, y: Float): Float = x % y
  }
  class IREM extends REM[Int]{
    override def zero: Int = 0
    override def rem(x: Int, y: Int): Int = x % y
  }
  class LREM extends REM[Long]{
    override def zero: Long = 0L
    override def rem(x: Long, y: Long): Long = x % y
  }

}

abstract class REM[@specialized(Double, Float, Int, Long) T](implicit val ev: ClassTag[T])
  extends Index8Instruction{

  def rem(v1: T, v2: T): T

  def zero: T

  override def execute(frame: Frame): Unit = {
    val stack = frame.operandStack
    val v2 = stack.pop[T]
    val v1 = stack.pop[T]
    if(v2 == zero){
      throw new Exception("rem on zero value")
    }
    rem(v1, v2)
  }
}
