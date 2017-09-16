package com.sword865.scalaJVM.rtda.heap

import javax.management.Descriptor

import com.sword865.scalaJVM.classfile.ClassFile

object ClassStruct {
  def apply(cf: ClassFile): ClassStruct = {
    val accessFlags = cf.accessFlags
    val name = cf.className
    val superClassName = cf.superClassName
    val interfaceNames = cf.interfaceNames()
    val classStruct = new ClassStruct(accessFlags, name, superClassName, interfaceNames)
    val fields = Field.newFields(classStruct, cf.fields)
    val methods = Method.newMethods(classStruct, cf.methods)
    val constantPool = ConstantPool(cf.constantPool, classStruct)
    classStruct.fields = fields
    classStruct.methods = methods
    classStruct.constantPool = constantPool
    classStruct
  }
}


class ClassStruct(val accessFlags: Int, val name: String, val superClassName: String,
                  val interfaceNames: Array[String], var fields: Array[Field] = null,
                  var methods: Array[Method] = null, var loader: ClassLoader = null,
                  var superClass: ClassStruct = null, var interfaces: Array[ClassStruct] = null,
                  var instanceSlotCount: Int = 0, var staticSlotCount: Int = 0,
                  var staticVars: Slots = null, var constantPool: ConstantPool = null) {

  def isPublic: Boolean = {
    0 != (accessFlags&ACC_PUBLIC)
  }
  def isFinal: Boolean = {
    0 != (accessFlags&ACC_FINAL)
  }
  def isSuper: Boolean = {
    0 != (accessFlags&ACC_SUPER)
  }
  def isInterface: Boolean = {
    0 != (accessFlags&ACC_INTERFACE)
  }
  def isAbstract: Boolean = {
    0 != (accessFlags&ACC_ABSTRACT)
  }
  def isSynthetic: Boolean = {
    0 != (accessFlags&ACC_SYNTHETIC)
  }
  def isAnnotation: Boolean = {
    0 != (accessFlags&ACC_ANNOTATION)
  }
  def isEnum: Boolean = {
    0 != (accessFlags&ACC_ENUM)
  }

  def isAccessibleTo(other: ClassStruct): Boolean = {
    if(isPublic){
      true
    }else{
      getPackageName == other.getPackageName
    }
  }

  def getPackageName: String = {
    val i = name.lastIndexOf("/")
    name.substring(0, i)
  }

  def getMainMethod: Method ={
    getStaticMethod("main", "([Ljava/lang/String;)V")
  }

  def getStaticMethod(name: String, descriptor: String): Method={
    methods.find(m=>m.isStatic&&m.name==name&&m.descriptor==descriptor).orNull
  }

  def isAssignableFrom(other: ClassStruct): Boolean = {
    val (s, t) = (other, this)
    if(s==t){
      true
    }else{
      if(!t.isInterface){
        s.isSubClassOf(t)
      }else{
        s.isImplements(t)
      }
    }
  }

  def isImplements(iface: ClassStruct): Boolean = {
    var c = this
    var finded = false
    while(!finded && c!=null) {
      finded = c.interfaces.exists(i=>i==iface||i.isSubInterfaceOf(iface))
      if(!finded) {
        c = c.superClass
      }
    }
    finded
  }

  def isSubInterfaceOf(iface: ClassStruct): Boolean = {
    interfaces.exists(i=>i==iface||i.isSubInterfaceOf(iface))
  }

  def isSubClassOf(other: ClassStruct): Boolean = {
    var c = superClass
    while(c!=other && c!=null){
      c = c.superClass
    }
    if(c==other){
      true
    }else{
      false
    }
  }


}
