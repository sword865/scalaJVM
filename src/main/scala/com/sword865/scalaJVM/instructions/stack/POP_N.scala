package com.sword865.scalaJVM.instructions.stack

import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction
import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction._
import com.sword865.scalaJVM.rtda.Frame

import scala.reflect.ClassTag

object POP_N{
  type POP = POP_N[ONE]
  type POP2 =POP_N[TWO]
}

class POP_N[V <: VALUE](implicit val vev: ClassTag[V]) extends NoOperandsInstruction{
  val count: Short = convertClassToValue(vev)

  override def execute(frame: Frame): Unit = {
    val stack = frame.operandStack
    (1 to count).foreach(_ => stack.popSlot())
  }
}
