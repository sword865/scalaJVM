package com.sword865.scalaJVM.rtda.heap

import com.sword865.scalaJVM.classfile

object MethodRef{
  def apply(cp: ConstantPool, refInfo: classfile.constantInfos.ConstantMethodrefInfo): MethodRef = {
    val ref = new MethodRef(cp)
    ref.copyMemberRefInfo(refInfo)
    ref
  }
}

class MethodRef (cp: ConstantPool = null) extends MemberRef(cp){
  var method: Method = _

  def resolvedMethod(): Method = {
    if(method == null){
      resolveMethodRef()
    }
    method
  }

  def resolveMethodRef(): Unit = {
    val d = cp.classStruct
    val c = resolvedClass()
    val method = lookupMethod(c, name, descriptor)
    if(method == null){
      throw new Exception("java.lang.NoSuchMethodError")
    }
    if(!method.isAccessibleTo(d)){
      throw new Exception("java.lang.IllegalAccessError")
    }
  }


  def lookupMethod(classStruct: ClassStruct, name: String, descriptor: String): Method = {
    val method = lookupMethodInClass(classStruct, name, descriptor)
    if(method == null){
      lookupMethodInInterfaces(classStruct.interfaces, name, descriptor)
    }else{
      method
    }
  }
}
