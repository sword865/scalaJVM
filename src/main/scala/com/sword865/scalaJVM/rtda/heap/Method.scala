package com.sword865.scalaJVM.rtda.heap

import com.sword865.scalaJVM.classfile.MemberInfo
import com.sword865.scalaJVM.classfile.attributeInfos.CodeAttribute

object Method{
  def newMethods(classStruct: ClassStruct, cfMethods: Array[MemberInfo]): Array[Method]={
    cfMethods.map(cfMethod =>
        Method(classStruct, cfMethod)
    )
  }

  def apply(classStruct: ClassStruct, cfMethod: MemberInfo): Method = {
    val codeAttr = cfMethod.codeAttribute

    new Method(cfMethod, classStruct, codeAttr)
    //if(codeAttr != null){
    //  val method = new
    //}else{
    //  new Method(cfMethod, classStruct)
    //Method(cfMethod, classStruct, codeAttr)}

  }
}


class Method (memberInfo: MemberInfo, classStruct: ClassStruct, codeAttr: CodeAttribute)
  extends ClassMember(memberInfo, classStruct){

  val maxStack: Int = if(codeAttr==null) 0 else codeAttr.maxStack
  val maxLocals: Int = if(codeAttr==null) 0 else codeAttr.maxLocals
  val code: Array[Byte] = if(codeAttr==null) null else codeAttr.code
  val argSlotCount: Int = {
    val parsedDescriptor = MethodDescriptorParser.parseMethodDescriptor(descriptor)
    val value = parsedDescriptor.parameterTypes.size+parsedDescriptor.parameterTypes.count(x=>x=="J"||x=="D")
    if(!isStatic){
      // +1 for this reference
      value + 1
    }else{
      value
    }
  }

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
