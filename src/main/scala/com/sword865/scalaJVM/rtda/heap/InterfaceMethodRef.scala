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

}
