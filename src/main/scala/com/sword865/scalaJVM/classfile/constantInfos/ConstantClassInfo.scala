package com.sword865.scalaJVM.classfile.constantInfos

import com.sword865.scalaJVM.classfile.{ClassReader, ConstantInfo, ConstantPool}

/**
  * Created by tianhaowei on 2017/9/7.
  */

object ConstantClassInfo {
  def apply(reader: ClassReader, cp: ConstantPool): ConstantClassInfo = {
    val nameIndex = reader.readUInt16()
    new ConstantClassInfo(cp, nameIndex)
  }
}

class ConstantClassInfo(cp: ConstantPool, val nameIndex: Int)  extends ConstantInfo {
  def name: String = cp.getUtf8(nameIndex)
}
