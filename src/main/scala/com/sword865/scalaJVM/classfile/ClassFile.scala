package com.sword865.scalaJVM.classfile

import com.sword865.scalaJVM.classfile.attributeInfos.SourceFileAttribute

/**
  * Created by tianhaowei on 2017/9/6.
  */

object ClassFile {

  def readAndCheckMagic(reader: ClassReader) : Boolean = {
    val magic = reader.readInt()
    if(magic != 0xCAFEBABE){
      throw new Exception("bad magic number")
    }else{
      true
    }
  }

  def readAndCheckVersion(reader: ClassReader) : (Int, Int) = {
    val minjorVersion = reader.readUInt16()
    val majorVersion = reader.readUInt16()
    majorVersion match {
      case 45 => (minjorVersion, majorVersion)
      case 46|47|48|49|50|51|52 if minjorVersion == 0 => (minjorVersion, majorVersion)
      case _ => throw new Exception(s"bad class file version: $majorVersion.$minjorVersion")
    }
  }

  def apply(reader: ClassReader): ClassFile = {
    readAndCheckMagic(reader)
    val (minorVersion, majorVersion) = readAndCheckVersion(reader)
    val constantPool: ConstantPool = ConstantPool(reader)
    val accessFlags: Int = reader.readUInt16()
    val thisClass: Int = reader.readUInt16()
    val superClass: Int = reader.readUInt16()
    val interfaces: Array[Int] = reader.readUInt16s()
    val fields: Array[MemberInfo] = MemberInfo.ArrayFrom(reader, constantPool)
    val methods: Array[MemberInfo] = MemberInfo.ArrayFrom(reader, constantPool)
    val attributes: Array[AttributeInfo] = AttributeInfo.ArrayFrom(reader, constantPool)
    new ClassFile(minorVersion,majorVersion,constantPool,accessFlags,
      thisClass,superClass,interfaces,fields,methods,attributes)
  }

  def parse(classData: Array[Byte]): ClassFile = {
    ClassFile(ClassReader(classData))
  }

}

case class ClassFile(minorVersion: Int, majorVersion: Int, constantPool: ConstantPool, accessFlags: Int,
                     thisClass: Int, superClass: Int, interfaces: Array[Int], fields: Array[MemberInfo],
                     methods: Array[MemberInfo], attributes: Array[AttributeInfo]){
  def sourceFileAttribute: SourceFileAttribute = {
    attributes.find(_.isInstanceOf[SourceFileAttribute]).map(_.asInstanceOf[SourceFileAttribute]).orNull
  }


  def className: String = constantPool.getClassName(thisClass)

  def superClassName: String = {
    if(superClass > 0){
      constantPool.getClassName(superClass)
    }else{
      ""
    }
  }

  def interfaceNames(): Array[String] = {
    interfaces.map(constantPool.getClassName)
  }
}
