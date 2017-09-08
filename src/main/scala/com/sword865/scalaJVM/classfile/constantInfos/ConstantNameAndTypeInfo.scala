package com.sword865.scalaJVM.classfile.constantInfos

import com.sword865.scalaJVM.classfile.{ClassReader, ConstantInfo, ConstantPool}

/**
  * Created by tianhaowei on 2017/9/7.
  */

object ConstantNameAndTypeInfo {
  def apply(reader: ClassReader): ConstantNameAndTypeInfo = {
    val nameIndex = reader.readUInt16()
    val descriptorIndex = reader.readUInt16()
    new ConstantNameAndTypeInfo(nameIndex, descriptorIndex)
  }
}

class ConstantNameAndTypeInfo(val nameIndex: Int, val descriptorIndex: Int) extends ConstantInfo
