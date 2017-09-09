package com.sword865.scalaJVM.instructions.comparisons

import com.sword865.scalaJVM.instructions.base.{BranchInstruction, Instruction}
import com.sword865.scalaJVM.rtda.Frame

import scala.reflect.ClassTag

object IF_CMP{
  class IF_ACMPEQ extends IF_CMP[AnyRef]{
    override def compare(v1: AnyRef, v2: AnyRef): Boolean = v1 == v2
  }
  class IF_ACMPNE extends IF_CMP[AnyRef]{
    override def compare(v1: AnyRef, v2: AnyRef): Boolean = v1 != v2
  }
  class IF_ICMPEQ extends IF_CMP[Int]{
    override def compare(v1: Int, v2: Int): Boolean = v1 == v2
  }
  class IF_ICMPNE extends IF_CMP[Int]{
    override def compare(v1: Int, v2: Int): Boolean = v1 != v2
  }
  class IF_ICMPLT extends IF_CMP[Int]{
    override def compare(v1: Int, v2: Int): Boolean = v1 < v2
  }
  class IF_ICMPLE extends IF_CMP[Int]{
    override def compare(v1: Int, v2: Int): Boolean = v1 <= v2
  }
  class IF_ICMPGT extends IF_CMP[Int]{
    override def compare(v1: Int, v2: Int): Boolean = v1 > v2
  }
  class IF_ICMPGE extends IF_CMP[Int]{
    override def compare(v1: Int, v2: Int): Boolean = v1 >= v2
  }
}


abstract class IF_CMP [@specialized(Int) T](implicit val ev: ClassTag[T])
  extends BranchInstruction{

  def compare(v1: T, v2: T): Boolean

  override def execute(frame: Frame): Unit = {
    val stack = frame.operandStack
    val v2 = stack.pop[T]
    val v1 = stack.pop[T]
    if(compare(v1,v2)){
      Instruction.branch(frame, offset)
    }
  }
}
