package com.sword865.scalaJVM.classfile.constantInfos

import com.sword865.scalaJVM.classfile.{ClassReader, ConstantInfo, ConstantPool}

/**
  * Created by tianhaowei on 2017/9/7.
  */

object ConstantFieldrefInfo {
  def apply(reader: ClassReader, cp: ConstantPool): ConstantFieldrefInfo = {
    val classIndex = reader.readUInt16()
    val nameAndTypeIndex = reader.readUInt16()
    new ConstantFieldrefInfo(cp, classIndex, nameAndTypeIndex)
  }
}
object ConstantMethodrefInfo {
  def apply(reader: ClassReader, cp: ConstantPool): ConstantMethodrefInfo = {
    val classIndex = reader.readUInt16()
    val nameAndTypeIndex = reader.readUInt16()
    new ConstantMethodrefInfo(cp, classIndex, nameAndTypeIndex)
  }
}
object ConstantInterfaceMethodrefInfo {
  def apply(reader: ClassReader, cp: ConstantPool): ConstantInterfaceMethodrefInfo = {
    val classIndex = reader.readUInt16()
    val nameAndTypeIndex = reader.readUInt16()
    new ConstantInterfaceMethodrefInfo(cp, classIndex, nameAndTypeIndex)
  }
}

class ConstantMemberrefInfo(cp: ConstantPool, classIndex: Int, nameAndTypeIndex: Int)  extends ConstantInfo {

  def className: String = cp.getClassName(classIndex)

  def getNameAndType: (String, String) = cp.getClassNameAndType(nameAndTypeIndex)
}


class ConstantFieldrefInfo(cp: ConstantPool, classIndex: Int, nameAndTypeIndex: Int)
  extends ConstantMemberrefInfo(cp,classIndex,nameAndTypeIndex)

class ConstantMethodrefInfo(cp: ConstantPool, classIndex: Int, nameAndTypeIndex: Int)
  extends ConstantMemberrefInfo(cp,classIndex,nameAndTypeIndex)

class ConstantInterfaceMethodrefInfo(cp: ConstantPool, classIndex: Int, nameAndTypeIndex: Int)
  extends ConstantMemberrefInfo(cp,classIndex,nameAndTypeIndex)