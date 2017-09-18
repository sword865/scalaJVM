package com.sword865.scalaJVM.rtda.heap

import com.sword865.scalaJVM.classfile.ClassFile
import com.sword865.scalaJVM.rtda.heap

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

  val primitiveTypes: Map[String, String] = Map[String, String]("void" -> "V", "boolean" -> "Z", "byte" -> "B",
    "short" -> "S", "int" -> "I", "long" -> "J",
    "char" -> "C", "float" -> "F", "double" -> "D")


  // [XXX => [XXX
  // int  => I
  // XXX  => LXXX;
  def toDecriptor(className: String): String = {
    if(className(0)=='['){
      className
    }else{
      primitiveTypes.getOrElse(className, s"L$className;")
    }
  }

  // [XXX  => [XXX
  // LXXX; => XXX
  // I     => int
  def toClassName(descriptor: String): String = {
    if(descriptor(0) == '['){
      descriptor
    }else if(descriptor(0) == 'L'){
      descriptor.drop(1).dropRight(1)
    }else{
      primitiveTypes.toStream.find(_._2 == descriptor).map(_._1).getOrElse(throw new Exception(s"Invalid descriptor: $descriptor"))
    }
  }

  // [XXX -> [[XXX
  // int -> [I
  // XXX -> [LXXX;
  def getArrayClassName(className: String): String = {
    "[" + toDecriptor(className)
  }

  // [[XXX -> [XXX
  // [LXXX; -> XXX
  // [I -> int
  def getComponentClassName(className: String): String ={
    if(className(0) == '['){
      val compoentTypeDescriptor = className.drop(1)
      toClassName(compoentTypeDescriptor)
    }else {
      throw new Exception(s"Not array: $className")
    }
  }

}


class ClassStruct(val accessFlags: Int, val name: String, val superClassName: String,
                  val interfaceNames: Array[String]=null, var initStarted: Boolean = false,
                  var fields: Array[Field] = null, var methods: Array[Method] = null,
                  var loader: ClassLoader = null, var superClass: ClassStruct = null,
                  var interfaces: Array[ClassStruct] = null, var instanceSlotCount: Int = 0,
                  var staticSlotCount: Int = 0, var staticVars: Slots = null,
                  var constantPool: ConstantPool = null, var jClass: heap.Object = null) {

  def isJioSerializable: Boolean = name == "java/io/Serializable"

  def isJlCloneable:Boolean = name == "java/lang/Cloneable"

  def isJlObject: Boolean = name == "java/lang/Object"

  def startInit(): Unit ={
    initStarted = true
  }

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
    getMethod("main", "([Ljava/lang/String;)V", true)
  }

  def getClinitMethod: Method = {
    getMethod("<clinit>", "()V", true)
  }

  def getMethod(name: String, descriptor: String, isStatic: Boolean): Method={
    var c = this
    var method: Method = null
    while(c!=null && method == null){
      method = c.methods.find(m=>m.name==name&&m.descriptor==descriptor&&m.isStatic==isStatic).orNull
      c = c.superClass
    }
    method
  }

  def getField(name: String, descriptor: String, isStatic: Boolean): Field ={
    var c = this
    var field: Field = null
    while(c!=null && field == null){
      field = c.fields.find(f=>f.name==name&&f.descriptor==descriptor&&f.isStatic==isStatic).orNull
      c = c.superClass
    }
    field
  }

  def isAssignableFrom(other: ClassStruct): Boolean = {
    val (s, t) = (other, this)
    if(s==t){
      true
    }else{
      if(!s.isArray) {
        if(!s.isInterface){
          if(!t.isInterface){
            s.isSubClassOf(t)
          }else{
            s.isImplements(t)
          }
        }else{
          if(!t.isInterface){
            t.isJlObject
          }else{
            t.isSuperInterfaceOf(s)
          }
        }
      }else{
        if(!t.isArray){
          if(!t.isInterface){
            t.isJlObject
          }else{
            t.isJlCloneable || t.isJioSerializable
          }
        }else{
          val sc = s.componentClass()
          val tc = t.componentClass()
          sc == tc || tc.isAssignableFrom(sc)
        }
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

  def isSuperClassOf(other: ClassStruct): Boolean ={
    other.isSubClassOf(this)
  }

  def isSuperInterfaceOf(iface: ClassStruct): Boolean ={
    iface.isSubInterfaceOf(this)
  }

  def newObject(): Object = {
    Object(this)
  }


  def isArray: Boolean = {
    name(0) == '['
  }

  def newArray(count: Int): Object = {
    if(!isArray){
      throw new Exception(s"Not array class $name")
    }
    name match{
      case "[Z" => Object(this, new Array[Byte](count))
      case "[B" => Object(this, new Array[Byte](count))
      case "[C" => Object(this, new Array[Char](count))
      case "[S" => Object(this, new Array[Short](count))
      case "[I" => Object(this, new Array[Int](count))
      case "[J" => Object(this, new Array[Long](count))
      case "[F" => Object(this, new Array[Float](count))
      case "[D" => Object(this, new Array[Double](count))
      case _ => Object(this, new Array[Object](count))
    }
  }

  def arrayClass(): ClassStruct = {
    val arrayClassName = ClassStruct.getArrayClassName(name)
    loader.loadClass(arrayClassName)
  }

  def componentClass(): ClassStruct = {
    val componentClassName = ClassStruct.getComponentClassName(name)
    loader.loadClass(componentClassName)
  }

}
