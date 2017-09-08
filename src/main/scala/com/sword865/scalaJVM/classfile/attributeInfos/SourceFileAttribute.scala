package com.sword865.scalaJVM.classfile.attributeInfos

import com.sword865.scalaJVM.classfile.{AttributeInfo, ClassReader, ConstantPool}

/**
  * Created by tianhaowei on 2017/9/7.
  */
object SourceFileAttribute{
  def apply(reader: ClassReader, cp: ConstantPool): SourceFileAttribute = {
    new SourceFileAttribute(cp, reader.readUInt16())
  }
}

class SourceFileAttribute(cp: ConstantPool, sourceFileIndex: Int) extends AttributeInfo{
  def fileName: String = cp.getUtf8(sourceFileIndex)
}
