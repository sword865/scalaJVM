package com.sword865.scalaJVM.instructions.constants

import com.sword865.scalaJVM.instructions.base.{BytecodeReader, Instruction}
import com.sword865.scalaJVM.rtda.Frame

import scala.reflect.ClassTag

object IPUSH{
  type BIPUSH =IPUSH[Byte]
  type SIPUSH =IPUSH[Short]
}

class IPUSH[T](implicit val ev: ClassTag[T]) extends Instruction{
  var value: Int = 0
  override def fetchOperands(reader: BytecodeReader): Unit = {
    if(ev==manifest[Byte]) {
      value = reader.readInt8()
    }else if(ev ==manifest[Short]){
      value = reader.readInt16()
    }
  }

  override def execute(frame: Frame): Unit = {
    frame.operandStack.pushInt(value.toInt)
  }
}