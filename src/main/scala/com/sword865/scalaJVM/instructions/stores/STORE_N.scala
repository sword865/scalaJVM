package com.sword865.scalaJVM.instructions.stores

import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction
import com.sword865.scalaJVM.instructions.base.NoOperandsInstruction._
import com.sword865.scalaJVM.rtda.{Frame, heap}

import scala.reflect.ClassTag

object STORE_N{
  class ASTORE_0 extends STORE_N[heap.Object,ZERO]
  class ASTORE_1 extends STORE_N[heap.Object,ONE]
  class ASTORE_2 extends STORE_N[heap.Object,TWO]
  class ASTORE_3 extends STORE_N[heap.Object,THREE]
  class DSTORE_0 extends STORE_N[Double,ZERO]
  class DSTORE_1 extends STORE_N[Double,ONE]
  class DSTORE_2 extends STORE_N[Double,TWO]
  class DSTORE_3 extends STORE_N[Double,THREE]
  class FSTORE_0 extends STORE_N[Float,ZERO]
  class FSTORE_1 extends STORE_N[Float,ONE]
  class FSTORE_2 extends STORE_N[Float,TWO]
  class FSTORE_3 extends STORE_N[Float,THREE]
  class ISTORE_0 extends STORE_N[Int,ZERO]
  class ISTORE_1 extends STORE_N[Int,ONE]
  class ISTORE_2 extends STORE_N[Int,TWO]
  class ISTORE_3 extends STORE_N[Int,THREE]
  class LSTORE_0 extends STORE_N[Long,ZERO]
  class LSTORE_1 extends STORE_N[Long,ONE]
  class LSTORE_2 extends STORE_N[Long,TWO]
  class LSTORE_3 extends STORE_N[Long,THREE]
}

class STORE_N[T, V <: VALUE](implicit val ev: ClassTag[T], implicit val vev: ClassTag[V])
  extends NoOperandsInstruction {

  val index: Short = convertClassToValue(vev)

  override def execute(frame: Frame): Unit = {
    val value = frame.operandStack.pop[T]
    frame.localVars.set[T](index, value)
  }

}
