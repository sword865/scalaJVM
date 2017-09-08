package com.sword865.scalaJVM.classfile.constantInfos

import com.sword865.scalaJVM.classfile.{ClassReader, ConstantInfo}

/**
  * Created by tianhaowei on 2017/9/7.
  */


object ConstantInvokeDynamicInfo{
  def apply(reader: ClassReader): ConstantInvokeDynamicInfo = {
    val bootstrapMethodAttrIndex = reader.readUInt16()
    val nameAndTypeIndex = reader.readUInt16()
    new ConstantInvokeDynamicInfo(bootstrapMethodAttrIndex, nameAndTypeIndex)
  }
}

class ConstantInvokeDynamicInfo(bootstrapMethodAttrIndex: Int, nameAndTypeIndex: Int) extends ConstantInfo