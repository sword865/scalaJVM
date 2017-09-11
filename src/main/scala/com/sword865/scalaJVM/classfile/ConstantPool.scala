package com.sword865.scalaJVM.classfile

import com.sword865.scalaJVM.classfile.constantInfos._

import scala.collection.mutable.ArrayBuffer

/**
  * Created by tianhaowei on 2017/9/6.
  */


object ConstantPool{

  def apply(reader: ClassReader): ConstantPool = {
    val constantPool = new ConstantPool()
    val cpCount = reader.readUInt16()
    val pool: Array[ConstantInfo] = new Array[ConstantInfo](cpCount)
    var i = 1
    while(i < cpCount){
      val constantInfo = ConstantInfo(reader, constantPool)
      pool(i) = constantInfo
      if(constantInfo.isInstanceOf[ConstantLongInfo] || constantInfo.isInstanceOf[ConstantDoubleInfo]){
        i = i + 2
      }else{
        i = i + 1
      }
    }
    constantPool.initPool(pool)
    constantPool
  }

}

class ConstantPool {

  var pool: Array[_ <: ConstantInfo] = _

  def length: Int = pool.length

  def initPool(pool: Array[_ <: ConstantInfo]): Unit ={
    this.pool = pool
  }

  def getConstantInfo(i: Int): ConstantInfo = {
    val cpInfo = pool.apply(i)
    if(cpInfo!=null){
      cpInfo
    }else{
      throw new Exception(s"Invalid constant pool index: $i")
    }
  }

  def getClassName(index: Int): String = {
    val classInfo = getConstantInfo(index).asInstanceOf[ConstantClassInfo]
    getUtf8(classInfo.nameIndex)
  }

  def getClassNameAndType(index: Int): (String, String) = {
    val ntInfo = getConstantInfo(index).asInstanceOf[ConstantNameAndTypeInfo]
    (getUtf8(ntInfo.nameIndex), getUtf8(ntInfo.descriptorIndex))
  }

  def getUtf8(index: Int): String = {
    val utf8Info = getConstantInfo(index).asInstanceOf[ConstantUtf8Info]
    utf8Info.str
  }

  def apply(i: Int): ConstantInfo ={
    pool.apply(i)
  }
}
