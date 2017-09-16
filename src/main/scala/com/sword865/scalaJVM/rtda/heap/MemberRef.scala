package com.sword865.scalaJVM.rtda.heap

import com.sword865.scalaJVM.classfile

class MemberRef(cp: ConstantPool) extends SymRef(cp){
  var name: String = _
  var descriptor: String = _

  def copyMemberRefInfo(refInfo: classfile.constantInfos.ConstantMemberrefInfo): Unit ={
    className = refInfo.className
    val value = refInfo.getNameAndType
    name = value._1
    descriptor = value._2
  }
}
