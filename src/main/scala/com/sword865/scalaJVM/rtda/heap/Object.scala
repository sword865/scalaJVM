package com.sword865.scalaJVM.rtda.heap

object Object{
  def apply(classStruct: ClassStruct): Object = {
    new Object(classStruct, Slots(classStruct.instanceSlotCount))
  }
}

class Object(val classStruct: ClassStruct, val fields: Slots) {
  def isInstanceOf(classStruct: ClassStruct): Boolean ={
    classStruct.isAssignableFrom(classStruct)
  }
}
