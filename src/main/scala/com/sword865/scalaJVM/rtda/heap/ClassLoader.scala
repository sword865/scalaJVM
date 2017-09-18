package com.sword865.scalaJVM.rtda.heap

import com.sword865.scalaJVM.classfile
import com.sword865.scalaJVM.classpath
import com.sword865.scalaJVM.classpath.Entry
import scala.collection.mutable.{Map => MMap}
object ClassLoader{
  def apply(cp: classpath.ClassPath, verboseFlag: Boolean=true): ClassLoader = {
    new ClassLoader(cp, verboseFlag)
  }
}

class ClassLoader(cp: classpath.ClassPath, verboseFlag: Boolean,
                  classMap: MMap[String, ClassStruct] = MMap[String, ClassStruct]()) {

  def loadClass(name: String): ClassStruct = {
    classMap.getOrElse(name, if(name(0) == '[') loadArrayClass(name) else loadNonArrayClass(name))
  }

  def loadArrayClass(name: String): ClassStruct = {
    val classStruct = new ClassStruct(
      accessFlags = ACC_PUBLIC,
      name = name,
      loader = this,
      initStarted = true,
      superClassName = "java/lang/Object",
      superClass = loadClass("java/lang/Object"),
      interfaceNames = Array[String]("java/lang/Cloneable", "java/io/Serializable"),
      interfaces = Array[ClassStruct](loadClass("java/lang/Cloneable"), loadClass("java/io/Serializable"))
    )
    classMap(name) = classStruct
    classStruct
  }

  def loadNonArrayClass(name: String): ClassStruct = {
    val(entry, data) = readClass(name)
    val classStruct = defineClass(data)
    link(classStruct)
    if(verboseFlag) {
      println(f"[Loaded $name from $entry]")
    }
    classStruct
  }

  def readClass(name: String): (Entry, Array[Byte]) = {
    val value = cp.readClass(name)
    val (entry, data) = if(value.nonEmpty){
      value.get
    }else{
      throw new Exception("java.lang.ClassNotFoundException:" + name)
    }
    (entry, data)
  }

  def resolveSuperClass(classStruct: ClassStruct): Unit = {
    if(classStruct.name != "java/lang/Object" ){
      classStruct.superClass = classStruct.loader.loadClass(classStruct.superClassName)
    }
  }

  def resolveInterfaces(classStruct: ClassStruct): Unit = {
    val interfaceCount = classStruct.interfaceNames.length
    if(interfaceCount > 0){
      classStruct.interfaces = classStruct.interfaceNames.map(classStruct.loader.loadClass)
    }
  }

  def defineClass(data: Array[Byte]): ClassStruct = {
    val classStruct = parseClass(data)
    classStruct.loader = this
    resolveSuperClass(classStruct)
    resolveInterfaces(classStruct)
    classMap(classStruct.name) = classStruct
    classStruct
  }

  def parseClass(data: Array[Byte]): ClassStruct = {
    val cf = classfile.ClassFile.parse(data)
    ClassStruct(cf)
  }

  def verify(classStruct: ClassStruct): Unit = {

  }

  def calcInstanceFieldSlotIds(classStruct: ClassStruct): Unit = {
    var slotId = 0
    if(classStruct.superClass!=null){
      slotId = classStruct.superClass.instanceSlotCount
    }
    for(field<-classStruct.fields){
      if(!field.isStatic){
        field.slotId = slotId
        slotId += 1
        if(field.isLongOrDouble){
          slotId += 1
        }
      }
    }
    classStruct.instanceSlotCount = slotId
  }

  def calcStaticFieldSlotIds(classStruct: ClassStruct): Unit ={
    var slotId = 0
    for(field<-classStruct.fields) {
      if (field.isStatic) {
        field.slotId = slotId
        slotId += 1
        if (field.isLongOrDouble) {
          slotId += 1
        }
      }
    }
    classStruct.staticSlotCount = slotId
  }

  def allocAndInitStaticVars(classStruct: ClassStruct): Unit = {
    classStruct.staticVars = Slots(classStruct.staticSlotCount)
    classStruct.fields.filter(x=>x.isStatic&&x.isFinal).foreach(x=>initStaticFinalVar(classStruct, x))
  }

  def initStaticFinalVar(classStruct: ClassStruct, field: Field): Unit ={
    val vars = classStruct.staticVars
    val cp = classStruct.constantPool
    val cpIndex = field.constValueIndex
    val slotId = field.slotId
    if(cpIndex>0) field.descriptor match{
      case "Z"|"B"|"C"|"S"|"I" =>
        val value = cp.getConstant(cpIndex).asInstanceOf[Int]
        vars.setInt(slotId, value)
      case "J" =>
        val value = cp.getConstant(cpIndex).asInstanceOf[Long]
        vars.setLong(slotId, value)
      case "F" =>
        val value = cp.getConstant(cpIndex).asInstanceOf[Float]
        vars.setFloat(slotId, value)
      case "D" =>
        val value = cp.getConstant(cpIndex).asInstanceOf[Double]
        vars.setDouble(slotId, value)
      case "Ljava/lang/String;" => throw new Exception("to do")
    }
  }

  def prepare(classStruct: ClassStruct): Unit = {
    calcInstanceFieldSlotIds(classStruct)
    calcStaticFieldSlotIds(classStruct)
    allocAndInitStaticVars(classStruct)
  }

  def link(classStruct: ClassStruct): Unit = {
    verify(classStruct)
    prepare(classStruct)
  }
}
