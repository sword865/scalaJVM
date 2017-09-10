package com.sword865.scalaJVM.instructions.stack

import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction
import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction._
import com.sword865.scalaJVM.rtda.Frame

import scala.reflect.ClassTag

object DUP_N_X{
  class DUP extends DUP_N_X[ONE, ZERO]
  class DUP_X1 extends DUP_N_X[ONE, ONE]
  class DUP_X2 extends DUP_N_X[ONE, TWO]
  class DUP2 extends DUP_N_X[TWO, ZERO]
  class DUP2_X1 extends DUP_N_X[TWO, ONE]
  class DUP2_X2 extends DUP_N_X[TWO, TWO]
}

class DUP_N_X[N<:VALUE, X <: VALUE](implicit val nev: ClassTag[N], implicit val xev: ClassTag[X])
  extends NoOperandsInstruction{
  val number: Short =  convertClassToValue(nev)
  val count: Short = convertClassToValue(xev)

  override def execute(frame: Frame): Unit = {
    val stack = frame.operandStack
    val slots = (1 to number).map(_ => stack.popSlot()).reverse

    slots.foreach(stack.pushSlot)

    val arr = (1 to count).map(_=>stack.popSlot()).reverse
    arr.foreach(stack.pushSlot)

    slots.foreach(stack.pushSlot)
  }
}
