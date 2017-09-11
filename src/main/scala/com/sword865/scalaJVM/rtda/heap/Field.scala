package com.sword865.scalaJVM.rtda.heap

import com.sword865.scalaJVM.classfile.MemberInfo

object Field{

  def newFields(classStruct: ClassStruct, cfFields: Array[MemberInfo]): Array[Field] = {
    cfFields.map({case cfField =>
        Field(classStruct, cfField)
    })

  }

  def apply(classStruct: ClassStruct, cfField: MemberInfo): Field = {
    val valAttr =cfField.constantValueAttribute
    val constValueIndex = if(valAttr != null){
     valAttr.constantValueIndex
    }else{
      0
    }
    new Field(cfField, classStruct, constValueIndex)
  }
}


class Field(memberInfo: MemberInfo, classStruct: ClassStruct,
            val constValueIndex: Int, val slotId: Int = 0)
  extends ClassMember(memberInfo, classStruct){

  def isVolatile: Boolean =
    0 != (accessFlags & ACC_VOLATILE)
  def isTransient: Boolean =
    0 != (accessFlags & ACC_TRANSIENT)
  def isEnum: Boolean =
    0 != (accessFlags & ACC_ENUM)
  def isLongOrDouble : Boolean =
    descriptor=="J" || descriptor == "D"
  
}
