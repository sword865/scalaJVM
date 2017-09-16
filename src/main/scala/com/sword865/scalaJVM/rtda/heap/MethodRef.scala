package com.sword865.scalaJVM.rtda.heap

import com.sword865.scalaJVM.classfile

object MethodRef{
  def apply(cp: ConstantPool, refInfo: classfile.constantInfos.ConstantMethodrefInfo): MemberRef = {
    val ref = new MemberRef(cp)
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

  }
}
