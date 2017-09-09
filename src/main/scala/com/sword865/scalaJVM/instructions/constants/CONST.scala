package com.sword865.scalaJVM.instructions.constants

import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction
import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction._
import com.sword865.scalaJVM.rtda.Frame
import scala.reflect.ClassTag

object CONST {

  type ACONST_NULL = CONST[AnyRef, ZERO]
  type DCONST_0 = CONST[Double, ZERO]
  type DCONST_1 = CONST[Double, ONE]
  type FCONST_0 = CONST[Float, ZERO]
  type FCONST_1 = CONST[Float, ONE]
  type FCONST_2 = CONST[Float, TWO]
  type ICONST_M1 = CONST[Int, MONE]
  type ICONST_0 = CONST[Int, ZERO]
  type ICONST_1 = CONST[Int, ONE]
  type ICONST_2 = CONST[Int, TWO]
  type ICONST_3 = CONST[Int, THREE]
  type ICONST_4 = CONST[Int, FOUR]
  type ICONST_5 = CONST[Int, FIVE]
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
    }else{
      frame.operandStack.pushRef(null)
    }
  }
}
