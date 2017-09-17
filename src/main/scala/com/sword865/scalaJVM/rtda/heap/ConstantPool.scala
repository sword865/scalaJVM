package com.sword865.scalaJVM.rtda.heap

import com.sword865.scalaJVM.classfile
import com.sword865.scalaJVM.classfile.constantInfos._
import com.sword865.scalaJVM.rtda.heap.ConstantPool.Constant

object ConstantPool{
  type Constant = Any
  def apply(cfCp: classfile.ConstantPool, classStruct: ClassStruct): ConstantPool ={
    var i = 1
    val consts = new Array[Constant](cfCp.length)
    val rtCp = new ConstantPool(classStruct, consts)
    while(i < cfCp.length) {
      val cpInfo = cfCp(i)
      cpInfo match {
        case c: ConstantIntegerInfo =>
          consts(i) = c.value
        case c: ConstantFloatInfo =>
          consts(i) = c.value
        case c: ConstantLongInfo =>
          consts(i) = c.value
          i += 1
        case c: ConstantDoubleInfo =>
          consts(i) = c.value
          i += 1
        case c: ConstantStringInfo =>
          consts(i) = c.toString
        case c: ConstantClassInfo =>
          consts(i) = ClassRef(rtCp, c)
        case c: ConstantFieldrefInfo =>
          consts(i) = FieldRef(rtCp, c)
        case c: ConstantMethodrefInfo =>
          consts(i) = MethodRef(rtCp, c)
        case c: ConstantInterfaceMethodrefInfo =>
          consts(i) = InterfaceMethodRef(rtCp, c)
        case _ =>
      }

      i += 1
    }
    rtCp
  }
}

class ConstantPool (val classStruct: ClassStruct, val consts: Array[Constant]){
  def apply(i: Int): Constant = consts.apply(i)
  def getConstant(i: Int): Constant  = apply(i)
}
