package com.sword865.scalaJVM.classfile.attributeInfos

import com.sword865.scalaJVM.classfile.{AttributeInfo, ClassReader, ConstantPool}

/**
  * Created by tianhaowei on 2017/9/7.
  */

object CodeAttribute{
  def apply(reader: ClassReader, cp: ConstantPool): CodeAttribute = {
    val maxStack = reader.readUInt16()
    val maxLocals = reader.readUInt16()
    val codeLength = reader.readUInt32()
    val code = reader.readBytes(codeLength)
    val exceptionTableEntry = ExceptionTableEntry.ArrayFrom(reader)
    val attributes = AttributeInfo.ArrayFrom(reader, cp)
    new CodeAttribute(cp, maxStack, maxLocals, code, exceptionTableEntry, attributes)
  }
}

class CodeAttribute(cp: ConstantPool,val maxStack: Int,val maxLocals: Int,val code: Array[Byte],
                    val exceptionTable: Array[ExceptionTableEntry], val attributes: Array[AttributeInfo])
  extends AttributeInfo

object ExceptionTableEntry{

  def ArrayFrom(reader: ClassReader): Array[ExceptionTableEntry] = {
    val tableLength = reader.readUInt16()
    (1 to tableLength).map(_ => ExceptionTableEntry(reader)).toArray
  }

  def apply(reader: ClassReader): ExceptionTableEntry ={
    val startPc = reader.readUInt16()
    val endPc = reader.readUInt16()
    val handlerPc = reader.readUInt16()
    val catchType = reader.readUInt16()
    new ExceptionTableEntry(startPc, endPc, handlerPc, catchType)
  }
}

class ExceptionTableEntry(val startPc: Int, val endPc: Int, val handlerPc: Int, val catchType: Int)
