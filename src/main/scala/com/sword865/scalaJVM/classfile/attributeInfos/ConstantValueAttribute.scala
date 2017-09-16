package com.sword865.scalaJVM.classfile.attributeInfos

import com.sword865.scalaJVM.classfile.{AttributeInfo, ClassReader}

/**
  * Created by tianhaowei on 2017/9/7.
  */
object ConstantValueAttribute{
  def apply(reader: ClassReader): ConstantValueAttribute = {
    new ConstantValueAttribute(reader.readUInt16())
  }
}

class ConstantValueAttribute(val constantValueIndex: Int) extends AttributeInfo{
}
