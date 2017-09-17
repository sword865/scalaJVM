package com.sword865.scalaJVM.rtda

package object heap {
  type Slots = LocalVars

  val ACC_PUBLIC = 0x0001 // class field method
  val ACC_PRIVATE = 0x0002 //       field method
  val ACC_PROTECTED = 0x0004 //       field method
  val ACC_STATIC = 0x0008 //       field method
  val ACC_FINAL = 0x0010 // class field method
  val ACC_SUPER = 0x0020 // class
  val ACC_SYNCHRONIZED = 0x0020 //             method
  val ACC_VOLATILE = 0x0040 //       field
  val ACC_BRIDGE = 0x0040 //             method
  val ACC_TRANSIENT = 0x0080 //       field
  val ACC_VARARGS = 0x0080 //             method
  val ACC_NATIVE = 0x0100 //             method
  val ACC_INTERFACE = 0x0200 // class
  val ACC_ABSTRACT = 0x0400 // class       method
  val ACC_STRICT = 0x0800 //             method
  val ACC_SYNTHETIC = 0x1000 // class field method
  val ACC_ANNOTATION = 0x2000 // class
  val ACC_ENUM = 0x4000 // class field


  def lookupMethodInClass(classStruct: ClassStruct, name: String, descriptor: String): Method = {
    var c = classStruct
    var method: Method = null
    while(method==null&&c!=null){
      method = c.methods.find(m=>m.name==name&&m.descriptor==descriptor).orNull
      c = c.superClass
    }
    method
  }

  def lookupMethodInInterfaces(ifaces: Array[ClassStruct], name: String, descriptor: String): Method = {
    ifaces.toStream.map(iface => {
      val m = iface.methods.find(m => m.name == name && m.descriptor == descriptor)
      m.getOrElse(lookupMethodInInterfaces(iface.interfaces, name, descriptor))
    }).find(_!=null).orNull
  }
}
