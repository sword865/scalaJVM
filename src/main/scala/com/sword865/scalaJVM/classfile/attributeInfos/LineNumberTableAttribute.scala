package com.sword865.scalaJVM.classfile.attributeInfos

import com.sword865.scalaJVM.classfile.{AttributeInfo, ClassReader}

/**
  * Created by tianhaowei on 2017/9/7.
  */

object LineNumberTableAttribute{
  def apply(reader: ClassReader): LineNumberTableAttribute = {
    val lineNumberTableLength = reader.readUInt16()
    new LineNumberTableAttribute((1 to lineNumberTableLength).map(_ => LineNumberTableEntry(reader)).toArray)
  }
}

class LineNumberTableAttribute(val lineNumberTable: Array[LineNumberTableEntry]) extends AttributeInfo{
  def getLineNumber(pc: Int): Int = {
    lineNumberTable.reverse.find(pc >= _.startPc).map(_.LineNumber).getOrElse(-1)
  }
}

object LineNumberTableEntry{
  def apply(reader: ClassReader): LineNumberTableEntry = {
    val startPc = reader.readUInt16()
    val LineNumber = reader.readUInt16()
    new LineNumberTableEntry(startPc, LineNumber)
  }
}


class LineNumberTableEntry(val startPc: Int, val LineNumber: Int) extends AttributeInfo
