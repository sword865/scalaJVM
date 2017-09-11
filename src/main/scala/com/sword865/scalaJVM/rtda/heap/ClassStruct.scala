package com.sword865.scalaJVM.rtda.heap

object ClassStruct {

}


class ClassStruct(val accessFlags: Int, val name: String, val superClassName: String,
                  val interfaceNames: Array[String], val constantPool: ConstantPool,
                  val fields: Array[Field], val methods: Array[Method], val loader: ClassLoader,
                  val superClass: ClassStruct, val interfaces: Array[ClassStruct],
                  instanceSlotCount: Int, staticSlotCount: Int, staticVars: Slots) {

  def isSubClassOf(other: ClassStruct): Boolean = true
  def getPackageName: String = ""

}
