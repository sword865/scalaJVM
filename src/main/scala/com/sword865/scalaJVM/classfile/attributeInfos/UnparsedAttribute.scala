package com.sword865.scalaJVM.classfile.attributeInfos

import com.sword865.scalaJVM.classfile.{AttributeInfo, ClassReader}

/**
  * Created by tianhaowei on 2017/9/7.
  */

object UnparsedAttribute{
  def apply(reader: ClassReader,name: String, length: Long, info: Array[Byte]): UnparsedAttribute = {
    new UnparsedAttribute(name, length, reader.readBytes(length))
  }
}

class UnparsedAttribute(name: String, length: Long, info: Array[Byte]) extends AttributeInfo
