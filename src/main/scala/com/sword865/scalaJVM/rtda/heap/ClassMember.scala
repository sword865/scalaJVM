package com.sword865.scalaJVM.rtda.heap

import com.sword865.scalaJVM.classfile.MemberInfo


class ClassMember(memberInfo: MemberInfo, val classStruct: ClassStruct){
  val accessFlags: Int = memberInfo.accessFlags
  val name: String = memberInfo.name
  val descriptor: String = memberInfo.descriptor
  def isPublic: Boolean = {
    0 != (accessFlags & ACC_PUBLIC)
  }

  def isPrivate: Boolean = {
    0 != (accessFlags & ACC_PRIVATE)
  }

  def isProtected: Boolean = {
    0 != (accessFlags & ACC_PROTECTED)
  }

  def isStatic: Boolean = {
    0 != (accessFlags & ACC_STATIC)
  }

  def isFinal: Boolean = {
    0 != (accessFlags & ACC_FINAL)
  }

  def isSynthetic: Boolean = {
    0 != (accessFlags & ACC_SYNTHETIC)
  }

  def isAccessibleTo(d: ClassStruct): Boolean ={
    val c = classStruct
    if(isPublic){
      true
    }else if(isProtected){
      d == c || d.isSubClassOf(c) || c.getPackageName == d.getPackageName
    }else if(!isPrivate){
      c.getPackageName == d.getPackageName
    }else{
      c == d
    }
  }
}

