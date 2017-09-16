package com.sword865.scalaJVM.rtda.heap

class SymRef( val cp: ConstantPool){
  var className: String = _
  var classStruct: ClassStruct = _

  def resolvedClass(): ClassStruct = {
    if(classStruct == null){
      resolveClassRef()
    }
    classStruct
  }

  def resolveClassRef(): Unit ={
    val d = cp.classStruct
    val c = d.loader.loadClass(className)
    if(!c.isAccessibleTo(d)){
      throw new Exception("java.lang.IllegalAccessError")
    }
    classStruct = c
  }
}
