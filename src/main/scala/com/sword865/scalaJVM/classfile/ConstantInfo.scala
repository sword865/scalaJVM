package com.sword865.scalaJVM.classfile

/**
  * Created by tianhaowei on 2017/9/7.
  */

import com.sword865.scalaJVM.classfile.constantInfos._

object ConstantInfo {
  type ConstantIntegerInfo = Int

  val CONSTANT_Class = 7
  val CONSTANT_Fieldref = 9
  val CONSTANT_Methodref = 10
  val CONSTANT_InterfaceMethodref = 11
  val CONSTANT_String = 8
  val CONSTANT_Integer = 3
  val CONSTANT_Float = 4
  val CONSTANT_Long = 5
  val CONSTANT_Double = 6
  val CONSTANT_NameAndType = 12
  val CONSTANT_Utf8 = 1
  val CONSTANT_MethodHandle = 15
  val CONSTANT_MethodType = 16
  val CONSTANT_InvokeDynamic = 18

  def apply(reader: ClassReader, cp: ConstantPool): ConstantInfo = {
    val tag = reader.readByte()
    tag match {
      case CONSTANT_Integer => ConstantIntegerInfo(reader)
      case CONSTANT_Float => ConstantFloatInfo(reader)
      case CONSTANT_Long => ConstantLongInfo(reader)
      case CONSTANT_Double => ConstantDoubleInfo(reader)
      case CONSTANT_Utf8 => ConstantUtf8Info(reader)
      case CONSTANT_String => ConstantStringInfo(reader, cp)
      case CONSTANT_Class => ConstantClassInfo(reader, cp)
      case CONSTANT_Fieldref => ConstantFieldrefInfo(reader, cp)
      case CONSTANT_Methodref => ConstantMethodrefInfo(reader, cp)
      case CONSTANT_InterfaceMethodref => ConstantInterfaceMethodrefInfo(reader, cp)
      case CONSTANT_NameAndType => ConstantNameAndTypeInfo(reader)
      case CONSTANT_MethodType => ConstantMethodTypeInfo(reader)
      case CONSTANT_MethodHandle => ConstantMethodHandleInfo(reader)
      case CONSTANT_InvokeDynamic => ConstantInvokeDynamicInfo(reader)
      case _ => throw new Exception("unknow tag exception")
    }
  }
}


class ConstantInfo
