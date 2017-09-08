package com.sword865.scalaJVM.classfile

/**
  * Created by tianhaowei on 2017/9/6.
  */
import com.sword865.scalaJVM.classfile.attributeInfos._

object AttributeInfo {

  def newAttributeInfo(attrName: String, attrLen: Long, cp: ConstantPool, reader: ClassReader): AttributeInfo = {
    attrName match {
      case "Code" => CodeAttribute(reader, cp)
      case "ConstantValue" => ConstantValueAttribute(reader)
      case "Deprecated" => DeprecatedAttribute(reader)
      case "Exceptions" => ExceptionsAttribute(reader)
      case "LineNumberTable" => LineNumberTableAttribute(reader)
      case "LocalVariableTable" => LocalVariableTableAttribute(reader)
      case "SourceFile" => SourceFileAttribute(reader, cp)
      case "Synthetic" => SyntheticAttribute(reader)
      case _ => UnparsedAttribute(reader, attrName, attrLen, null)
    }
  }

  def apply(reader: ClassReader, cp: ConstantPool): AttributeInfo = {
    val attrNameIndex = reader.readUInt16()
    val attrName = cp.getUtf8(attrNameIndex)
    val attrLen = reader.readUInt32()
    val attrInfo = newAttributeInfo(attrName, attrLen, cp, reader)
    attrInfo
  }

  def ArrayFrom(reader: ClassReader, cp: ConstantPool): Array[AttributeInfo] = {
    val attributesCount = reader.readUInt16()
    (1 to attributesCount).map(_ => AttributeInfo(reader, cp)).toArray
  }
}

class AttributeInfo
