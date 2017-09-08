package com.sword865.scalaJVM.classfile.constantInfos

import com.sword865.scalaJVM.classfile.{ClassReader, ConstantInfo, ConstantPool}

/**
  * Created by tianhaowei on 2017/9/7.
  */

object ConstantStringInfo {
  def apply(reader: ClassReader, cp: ConstantPool): ConstantStringInfo = {
    val stringIndex = reader.readUInt16()
    new ConstantStringInfo(cp, stringIndex)
  }
}

class ConstantStringInfo(cp: ConstantPool, stringIndex: Int)  extends ConstantInfo {
  override def toString: String = {
    cp.getUtf8(stringIndex)
  }
}
