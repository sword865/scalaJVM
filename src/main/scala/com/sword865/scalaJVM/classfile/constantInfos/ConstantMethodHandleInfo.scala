package com.sword865.scalaJVM.classfile.constantInfos

import com.sword865.scalaJVM.classfile.{ClassReader, ConstantInfo}

/**
  * Created by tianhaowei on 2017/9/7.
  */

object ConstantMethodHandleInfo{
  def apply(reader: ClassReader): ConstantMethodHandleInfo = {
    val referenceKind = reader.readByte().toInt
    val referenceIndex = reader.readUInt16()
    new ConstantMethodHandleInfo(referenceIndex, referenceIndex)
  }
}

class ConstantMethodHandleInfo(referenceKind: Int, referenceIndex: Int) extends ConstantInfo
