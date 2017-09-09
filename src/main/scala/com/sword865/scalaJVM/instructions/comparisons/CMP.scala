package com.sword865.scalaJVM.instructions.comparisons

import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction
import com.sword865.scalaJVM.rtda.Frame

import scala.reflect.ClassTag

object CMP{
  class LCMP extends CMP[Long]{
    override def compare(v1: Long, v2: Long): Int ={
      if(v1 > v2){
        1
      }else if(v1 == v2){
        0
      }else{
        -1
      }
    }
  }

  class FCMPG extends CMP[Float]{
    override def compare(v1: Float, v2: Float): Int ={
      if(v1 > v2){
        1
      }else if(v1 == v2){
        0
      }else if(v1 < v2){
        -1
      }else{
        1
      }
    }
  }

  class FCMPL extends CMP[Float]{
    override def compare(v1: Float, v2: Float): Int ={
      if(v1 > v2){
        1
      }else if(v1 == v2){
        0
      }else if(v1 < v2){
        -1
      }else{
        -1
      }
    }
  }

  class DCMPG extends CMP[Double]{
    override def compare(v1: Double, v2: Double): Int ={
      if(v1 > v2){
        1
      }else if(v1 == v2){
        0
      }else if(v1 < v2){
        -1
      }else{
        1
      }
    }
  }

  class DCMPL extends CMP[Double]{
    override def compare(v1: Double, v2: Double): Int ={
      if(v1 > v2){
        1
      }else if(v1 == v2){
        0
      }else if(v1 < v2){
        -1
      }else{
        -1
      }
    }
  }
}

abstract class CMP [@specialized(Int, Long, Float, Double) T](implicit val ev: ClassTag[T])
  extends NoOperandsInstruction{
  def compare(v1: T, v2: T): Int

  override def execute(frame: Frame): Unit = {
    val stack = frame.operandStack
    val v2 = stack.pop[T]
    val v1 = stack.pop[T]
    stack.pushInt(compare(v1, v2))
  }

}
