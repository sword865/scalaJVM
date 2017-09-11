package com.sword865.scalaJVM.rtda.heap

import com.sword865.scalaJVM.classfile.MemberInfo

object Method{
  def newMethods(classStruct: ClassStruct, cfMethods: Array[MemberInfo]): Array[Method]={
    cfMethods.map({ case cfMethod =>
        Method(classStruct, cfMethod)
    })
  }

  def apply(classStruct: ClassStruct, cfMethod: MemberInfo): Method = {
    val codeAttr = cfMethod.codeAttribute
    if(codeAttr != null){
      new Method(cfMethod, classStruct, codeAttr.maxStack, codeAttr.maxLocals, codeAttr.code)
    }else{
      new Method(cfMethod, classStruct)
    }
  }
}


class Method (memberInfo: MemberInfo, classStruct: ClassStruct,
              val maxStack: Int=0, val maxLocals: Int=0, val code: Array[Byte]=null)
  extends ClassMember(memberInfo, classStruct){
  def isSynchronized: Boolean =
    0 != (accessFlags & ACC_SYNCHRONIZED)
  def isBridge: Boolean =
    0 != (accessFlags & ACC_BRIDGE)
  def isVarargs: Boolean =
    0 != (accessFlags & ACC_VARARGS)
  def isNative: Boolean =
    0 != (accessFlags & ACC_NATIVE)
  def isAbstract: Boolean =
    0 != (accessFlags & ACC_ABSTRACT)
  def isStrict: Boolean =
    0 != (accessFlags & ACC_STRICT)

}
