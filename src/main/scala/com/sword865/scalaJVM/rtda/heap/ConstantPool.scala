package com.sword865.scalaJVM.rtda.heap

import com.sword865.scalaJVM.classfile
import com.sword865.scalaJVM.classfile.constantInfos._
import com.sword865.scalaJVM.rtda.heap.ConstantPool.Constant

object ConstantPool{
  type Constant = Any
  def apply(cfCp: classfile.ConstantPool, classStruct: ClassStruct): ConstantPool ={
    var i = 1
    val consts = new Array[Constant](cfCp.length)
    while(i < cfCp.length) {
      val cpInfo = cfCp(i)
      if(cpInfo.isInstanceOf[ConstantIntegerInfo]){

      }else if(cpInfo.isInstanceOf[ConstantStringInfo]){

      }
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

        case c: ConstantFieldrefInfo =>

        case c: ConstantMethodrefInfo =>

        case c: ConstantInterfaceMethodrefInfo =>

      }

      i += 1
    }
    new ConstantPool(classStruct, consts)
  }
}

class ConstantPool (classStruct: ClassStruct, val consts: Array[Constant]){
  def apply(i: Int): Constant = consts.apply(i)
}
