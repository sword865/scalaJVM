package com.sword865.scalaJVM.classfile.attributeInfos

import com.sword865.scalaJVM.classfile.{AttributeInfo, ClassReader}

/**
  * Created by tianhaowei on 2017/9/7.
  */

object LocalVariableTableAttribute{
  def apply(reader: ClassReader): LocalVariableTableAttribute = {
    val localVariableTableLength = reader.readUInt16()
    new LocalVariableTableAttribute((1 to localVariableTableLength).map(_ => LocalVariableTableEntry(reader)).toArray)
  }
}

class LocalVariableTableAttribute(localVariableTable: Array[LocalVariableTableEntry]) extends AttributeInfo

object LocalVariableTableEntry{
  def apply(reader: ClassReader): LocalVariableTableEntry = {
    val startPc = reader.readUInt16()
    val length = reader.readUInt16()
    val nameIndex = reader.readUInt16()
    val descriptorIndex = reader.readUInt16()
    val index = reader.readUInt16()
    new LocalVariableTableEntry(startPc, length, nameIndex, descriptorIndex, index)
  }
}

class LocalVariableTableEntry(val startPc: Int, val length: Int, val nameIndex: Int, val descriptorIndex: Int, val index: Int) extends AttributeInfo