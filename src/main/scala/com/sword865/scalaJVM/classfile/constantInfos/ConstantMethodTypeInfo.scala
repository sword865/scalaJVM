package com.sword865.scalaJVM.classfile.constantInfos

import com.sword865.scalaJVM.classfile.{ClassReader, ConstantInfo}

/**
  * Created by tianhaowei on 2017/9/7.
  */

object ConstantMethodTypeInfo{
  def apply(reader: ClassReader): ConstantMethodTypeInfo = {
    val descriptorIndex = reader.readUInt16()
    new ConstantMethodTypeInfo(descriptorIndex)
  }
}

class ConstantMethodTypeInfo(descriptorIndex: Int) extends ConstantInfo
