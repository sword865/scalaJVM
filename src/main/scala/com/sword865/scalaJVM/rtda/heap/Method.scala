package com.sword865.scalaJVM.rtda.heap

import com.sword865.scalaJVM.classfile.MemberInfo
import com.sword865.scalaJVM.classfile.attributeInfos.CodeAttribute

object Method{

  implicit def int2byte(int: Int): Byte = {
    int.toByte
  }

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

  var maxStack: Int = if(codeAttr==null) 0 else codeAttr.maxStack
  var maxLocals: Int = if(codeAttr==null) 0 else codeAttr.maxLocals
  var code: Array[Byte] = if(codeAttr==null) null else codeAttr.code
  val md: MethodDescriptor = MethodDescriptorParser.parseMethodDescriptor(descriptor)
  val argSlotCount: Int = {
    val value = md.parameterTypes.size+md.parameterTypes.count(x=>x=="J"||x=="D")
    if(!isStatic){
      // +1 for this reference
      value + 1
    }else{
      value
    }
  }
  if(isNative){
    injectCodeAttribute(md.returnType)
  }

  def injectCodeAttribute(returnType: String): Unit ={
    import Method.int2byte
    maxStack = 4
    maxLocals = argSlotCount
    returnType(0) match{
      case 'V' =>
        code = Array[Byte](0xfe, 0xb1) // return
      case 'L'|'[' =>
        code = Array[Byte](0xfe, 0xb0) // areturn
      case 'D' =>
        code = Array[Byte](0xfe, 0xaf) // dreturn
      case 'F' =>
        code = Array[Byte](0xfe, 0xae) // freturn
      case 'J' =>
        code = Array[Byte](0xfe, 0xad) // lreturn
      case _ =>
        code = Array[Byte](0xfe, 0xac) // ireturn
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
