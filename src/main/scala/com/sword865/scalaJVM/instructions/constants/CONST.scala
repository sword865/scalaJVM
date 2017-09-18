package com.sword865.scalaJVM.instructions.constants

import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction
import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction._
import com.sword865.scalaJVM.rtda.{Frame, heap}

import scala.reflect.ClassTag


object CONST {
  class ACONST_NULL extends CONST[heap.Object, ZERO]
  class DCONST_0 extends CONST[Double, ZERO]
  class DCONST_1 extends CONST[Double, ONE]
  class FCONST_0 extends CONST[Float, ZERO]
  class FCONST_1 extends CONST[Float, ONE]
  class FCONST_2 extends CONST[Float, TWO]
  class ICONST_M1 extends CONST[Int, MONE]
  class ICONST_0 extends CONST[Int, ZERO]
  class ICONST_1 extends CONST[Int, ONE]
  class ICONST_2 extends CONST[Int, TWO]
  class ICONST_3 extends CONST[Int, THREE]
  class ICONST_4 extends CONST[Int, FOUR]
  class ICONST_5 extends CONST[Int, FIVE]
  class LCONST_0 extends CONST[Long, ZERO]
  class LCONST_1 extends CONST[Long, ONE]
}

class CONST[T, V <: VALUE](implicit val ev: ClassTag[T], implicit val vev: ClassTag[V])
  extends NoOperandsInstruction {

  val value: Short = convertClassToValue(vev)

  override def execute(frame: Frame): Unit = {
    if(ev == manifest[Int]){
      frame.operandStack.pushInt(value.toInt)
    }else if(ev ==manifest[Float]){
      frame.operandStack.pushFloat(value.toFloat)
    }else if(ev == manifest[Double]){
      frame.operandStack.pushDouble(value.toDouble)
    }else if(ev == manifest[Long]){
      frame.operandStack.pushLong(value.toLong)
    }else{
      frame.operandStack.pushRef(null)
    }
  }
}
