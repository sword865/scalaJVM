package com.sword865.scalaJVM.instructions.math

import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction
import com.sword865.scalaJVM.rtda.Frame

import scala.reflect.ClassTag

object BOOL2{
  class IAND extends BOOL2[Int]{
    override def op(v1: Int, v2: Int): Int = v1 & v2
  }
  class IOR extends BOOL2[Int]{
    override def op(v1: Int, v2: Int): Int = v1 | v2
  }
  class IXOR extends BOOL2[Int]{
    override def op(v1: Int, v2: Int): Int = v1 ^ v2
  }
  class LAND extends BOOL2[Long]{
    override def op(v1: Long, v2: Long): Long = v1 & v2
  }
  class LOR extends BOOL2[Long]{
    override def op(v1: Long, v2: Long): Long = v1 | v2
  }
  class LXOR extends BOOL2[Long]{
    override def op(v1: Long, v2: Long): Long = v1 ^ v2
  }
}

abstract class BOOL2[@specialized(Int, Long) T](implicit val ev: ClassTag[T])
  extends NoOperandsInstruction{

  def op(v1: T ,v2: T): T

  override def execute(frame: Frame): Unit = {
    val stack = frame.operandStack
    val v1 = stack.pop[T]
    val v2 = stack.pop[T]
    op(v1, v2)
  }
}
