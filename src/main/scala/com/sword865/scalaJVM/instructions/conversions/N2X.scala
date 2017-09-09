package com.sword865.scalaJVM.instructions.conversions

import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction
import com.sword865.scalaJVM.rtda.Frame

import scala.reflect.ClassTag

object N2X{
  class D2F extends N2X[Double, Float]{
    override def convert(t: Double): Float = t.toFloat
  }
  class D2I extends N2X[Double, Int]{
    override def convert(t: Double): Int = t.toInt
  }
  class D2L extends N2X[Double, Long]{
    override def convert(t: Double): Long = t.toLong
  }

  class F2D extends N2X[Float, Double]{
    override def convert(t: Float): Double = t.toDouble
  }
  class F2I extends N2X[Float, Int]{
    override def convert(t: Float): Int = t.toInt
  }
  class F2L extends N2X[Float, Long]{
    override def convert(t: Float): Long = t.toLong
  }

  class I2D extends N2X[Int, Double]{
    override def convert(t: Int): Double = t.toDouble
  }
  class I2F extends N2X[Int, Float]{
    override def convert(t: Int): Float = t.toInt
  }
  class I2L extends N2X[Int, Long]{
    override def convert(t: Int): Long = t.toLong
  }
  class I2B extends N2X[Int, Int]{
    override def convert(t: Int): Int = t & 0xFF
  }
  class I2C extends N2X[Int, Int]{
    override def convert(t: Int): Int = t & 0xFFFF
  }
  class I2S extends N2X[Int, Int]{
    override def convert(t: Int): Int = t & 0xFFFF
  }

  class L2D extends N2X[Long, Double]{
    override def convert(t: Long): Double = t.toDouble
  }
  class L2F extends N2X[Long, Float]{
    override def convert(t: Long): Float = t.toInt
  }

}

abstract class N2X[@specialized(Int, Long, Float, Double) T,
  @specialized(Int, Long, Float, Double) G](implicit val ev: ClassTag[T], implicit val gev: ClassTag[G])
  extends NoOperandsInstruction{

  def convert(t: T): G

  override def execute(frame: Frame): Unit = {
    val stack = frame.operandStack
    val v = stack.pop[T]
    stack.push[G](convert(v))
  }
}
