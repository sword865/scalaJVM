package com.sword865.scalaJVM.rtda.heap

import com.sword865.scalaJVM.classfile

object InterfaceMethodRef{
  def apply(cp: ConstantPool, refInfo: classfile.constantInfos.ConstantInterfaceMethodrefInfo): InterfaceMethodRef = {
    val ref = new InterfaceMethodRef(cp)
    ref.copyMemberRefInfo(refInfo)
    ref
  }
}

class InterfaceMethodRef(cp: ConstantPool = null) extends MemberRef(cp){
  var method: Method = _

  def resolvedInterfaceMethod(): Method ={
    if(method == null){
      resolveInterfaceMethodRef()
    }
    method
  }

  def lookupInterfaceMethod(iface: ClassStruct, name: String, descriptor: String): Method = {
    val method = iface.methods.find(m=>m.name==name&&m.descriptor==descriptor)
    method.getOrElse(lookupMethodInInterfaces(iface.interfaces, name, descriptor))
  }

  def resolveInterfaceMethodRef(): Unit ={
    val d = cp.classStruct
    val c = resolvedClass()
    if(!c.isInterface){
      throw new Exception("java.lang.IncompatibleClassChangeError")
    }
    val value = lookupInterfaceMethod(c, name, descriptor)
    if(value == null){
      throw new Exception("java.lang.NoSuchMethodError")
    }
    if(!value.isAccessibleTo(d)) {
      throw new Exception("java.lang.IllegalAccessError")
    }
    method = value
  }

}
