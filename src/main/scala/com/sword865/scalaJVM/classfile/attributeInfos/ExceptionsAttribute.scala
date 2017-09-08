package com.sword865.scalaJVM.classfile.attributeInfos

import com.sword865.scalaJVM.classfile.{AttributeInfo, ClassReader}

/**
  * Created by tianhaowei on 2017/9/7.
  */
object ExceptionsAttribute{
  def apply(reader: ClassReader): ExceptionsAttribute = {
    new ExceptionsAttribute(reader.readUInt16s())
  }
}

class ExceptionsAttribute(val exceptionIndexTable: Array[Int]) extends AttributeInfo