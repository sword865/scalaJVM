package com.sword865.scalaJVM.classfile

import com.sword865.scalaJVM.classfile.attributeInfos.{CodeAttribute, ConstantValueAttribute}

/**
  * Created by tianhaowei on 2017/9/6.
  */

object MemberInfo {

  def apply(reader: ClassReader, cp: ConstantPool): MemberInfo = {
    val accessFlags = reader.readUInt16()
    val nameIndex = reader.readUInt16()
    val descriptorIndex = reader.readUInt16()
    val attributes = AttributeInfo.ArrayFrom(reader, cp)
    new MemberInfo(cp, accessFlags, nameIndex, descriptorIndex, attributes)
  }

  def ArrayFrom(reader: ClassReader, cp: ConstantPool): Array[MemberInfo] = {
    val memberCount = reader.readUInt16()
    (1 to memberCount).map(_ => MemberInfo(reader, cp)).toArray
  }
}

class MemberInfo(cp: ConstantPool, val accessFlags: Int, nameIndex: Int, descriptorIndex: Int, attributes: Array[AttributeInfo] ) {
  def name: String = cp.getUtf8(nameIndex)
  def descriptor: String = cp.getUtf8(descriptorIndex)
  def codeAttribute: CodeAttribute =
    attributes.find(x=>x.isInstanceOf[CodeAttribute]).map(x=>x.asInstanceOf[CodeAttribute]).orNull
  def constantValueAttribute: ConstantValueAttribute =
    attributes.find(x=>x.isInstanceOf[ConstantValueAttribute]).map(x=>x.asInstanceOf[ConstantValueAttribute]).orNull
}
