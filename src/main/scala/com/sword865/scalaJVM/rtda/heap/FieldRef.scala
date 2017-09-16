package com.sword865.scalaJVM.rtda.heap

import com.sword865.scalaJVM.classfile

object FieldRef{
  def apply(cp: ConstantPool, refInfo: classfile.constantInfos.ConstantFieldrefInfo): FieldRef = {
    val ref = new FieldRef(cp)
    ref.copyMemberRefInfo(refInfo)
    ref
  }
}

class FieldRef(cp: ConstantPool = null) extends MemberRef(cp){
  var field: Field = _
  def resolvedField(): Field = {
    if(field == null){
      resolveFieldRef()
    }
    field
  }
  def resolveFieldRef(): Unit ={
    val d =  cp.classStruct
    val c = resolvedClass()
    val field = lookupField(c, name, descriptor)
  }

  def lookupField(c: ClassStruct, name: String, descriptor: String): Field ={
    val f = c.fields.find(f=>f.name==name&&f.descriptor==descriptor)
    if(f.isDefined){
      f.get
    }else{
      val f1 = c.interfaces.toStream.map(f=>lookupField(f, name, descriptor)).filter(_!=null).headOption
      if(f1.isDefined){
        f1.get
      }else if(c.superClass != null){
        lookupField(c.superClass, name, descriptor)
      }else {
        null
      }
    }
  }

}
