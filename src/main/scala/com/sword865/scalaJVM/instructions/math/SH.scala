package com.sword865.scalaJVM.instructions.math

import com.sword865.scalaJVM.instructions.base.Index8Instruction

import scala.reflect.ClassTag
import com.sword865.scalaJVM.instructions.math.SH._
import com.sword865.scalaJVM.rtda.Frame

object SH {

  class ISHL extends SH[Int] {
    override def move(v1: Int, v2: Int): Int = v1 << v2
  }

  class ISHR extends SH[Int] {
    override def move(v1: Int, v2: Int): Int = v1 >> v2
  }

  class IUSHR extends SH[Int] {
    override def move(v1: Int, v2: Int): Int = v1 >>> v2
  }

  class LSHL extends SH[Long] {
    override def move(v1: Long, v2: Int): Long = v1 << v2
  }

  class LSHR extends SH[Long] {
    override def move(v1: Long, v2: Int): Long = v1 >> v2
  }

  class LUSHR extends SH[Long] {
    override def move(v1: Long, v2: Int): Long = v1 >>> v2
  }

}

abstract class SH[@specialized(Int, Long) T](implicit val ev: ClassTag[T])
  extends Index8Instruction{

  def move(v1: T, v2:Int): T

  override def execute(frame: Frame): Unit = {
    val stack = frame.operandStack
    val v2 = stack.popInt()
    val v1 = stack.pop[T]
    stack.push[T](move(v1, v2))
  }
}
