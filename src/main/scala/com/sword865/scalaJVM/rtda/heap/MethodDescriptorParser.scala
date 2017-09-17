package com.sword865.scalaJVM.rtda.heap

object MethodDescriptorParser{

  def parseMethodDescriptor(descriptor: String): MethodDescriptor ={
    val parser = new MethodDescriptorParser()
    parser.parse(descriptor)
  }
}

class MethodDescriptorParser(){
  var raw: String = _
  var offset: Int = 0
  var parsed: MethodDescriptor = _

  def parse(descriptor: String): MethodDescriptor ={
    raw = descriptor
    parsed = new MethodDescriptor()
    startParams()
    parseParamTypes()
    endParams()
    parseReturnType()
    finish()
    parsed
  }

  def startParams():Unit = {
    if(readUint8() != '('){
      causePanic()
    }
  }

  def endParams():Unit = {
    if(readUint8() != ')'){
      causePanic()
    }
  }

  def finish():Unit = {
    if(offset != raw.length) {
      causePanic()
    }
  }

  def parseParamTypes():Unit = {
    var flag = true
    while(flag){
      val t = parseFieldType()
      if(t!=""){
        parsed.addParameterType(t)
      }else{
        flag = false
      }
    }
  }

  def parseReturnType():Unit = {
    if(readUint8()=='V'){
      parsed.returnType = "V"
    }else{
      unreadUint8()
      val t = parseFieldType()
      if(t != ""){
        parsed.returnType = t
      }else{
        causePanic()
      }
    }
  }

  def parseFieldType():String = {
    val t = readUint8()
    t match {
      case 'B'|'C'|'D'|'F'|'I'|'J'|'S'|'Z' =>
        t.toString
      case 'L' =>
        parseObjectType()
      case '[' =>
        parseArrayType()
      case _ =>
        unreadUint8()
        ""
    }
  }

  def parseObjectType():String = {
    val unread = raw.substring(offset)
    val semicolonIndex = unread.indexOf(';')
    if(semicolonIndex == -1){
      causePanic()
      ""
    }else{
      val objStart = offset - 1
      val objEnd = offset + semicolonIndex + 1
      offset = objEnd
      val descriptor = raw.substring(objStart, objEnd)
      descriptor
    }
  }

  def parseArrayType():String = {
    val arrStart = offset - 1
    parseFieldType()
    val arrEnd = offset
    val descriptor = raw.substring(arrStart, arrEnd)
    descriptor
  }

  def readUint8(): Char = {
    val b = raw(offset)
    offset += 1
    b
  }

  def unreadUint8(): Unit ={
    offset -= 1
  }

  def causePanic(): Unit ={
    throw new Exception(s"BAD descriptor: $raw")
  }
}
