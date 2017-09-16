package com.sword865.scalaJVM.rtda.heap

import com.sword865.scalaJVM.classfile

object ClassRef {

  def apply(classInfo: classfile.constantInfos.ConstantClassInfo, cp: ConstantPool = null): ClassRef = {
    val ref = new ClassRef(cp)//, classInfo.name, null)
    ref.className = classInfo.name
    ref
  }
}


class ClassRef(cp: ConstantPool = null)
  extends SymRef(cp){

}
