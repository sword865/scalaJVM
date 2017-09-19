package com.sword865.scalaJVM.rtda.heap

import com.sword865.scalaJVM.classfile.attributeInfos.ExceptionTableEntry
import com.sword865.scalaJVM.rtda.heap.ExceptionTable.ExceptionHandler

/**
  * Created by tianhaowei on 2017/9/19.
  */
object ExceptionTable{

  case class ExceptionHandler(startPc: Int, endPc: Int, handlerPc: Int, catchType: ClassRef)

  def getCatchType(index: Int, cp: ConstantPool): ClassRef = {
    if(index == 0){
      return null // catch all
    }
    cp.getConstant(index).asInstanceOf[ClassRef]
  }

  def apply(entries: Array[ExceptionTableEntry], cp: ConstantPool): ExceptionTable = {
    val table = entries.map(entry => {
      val startPc = entry.startPc
      val endPc = entry.endPc
      val handlerPc = entry.handlerPc
      val cacheType = getCatchType(entry.catchType, cp)
      ExceptionHandler(startPc, endPc, handlerPc, cacheType)
    })
    new ExceptionTable(table)
  }

}

class ExceptionTable(table: Array[ExceptionTable.ExceptionHandler]){

  def findExceptionHandler(exClass: ClassStruct, pc: Int): ExceptionHandler = {
      // jvms: The start_pc is inclusive and end_pc is exclusive
    table.find(handler => {
      if(pc >= handler.startPc && pc < handler.endPc){
        if(handler.catchType == null){
          true
        }else{
          val catchClass = handler.catchType.resolvedClass()
          if(catchClass == exClass || catchClass.isSuperClassOf(exClass)){
            true
          }else{
            false
          }
        }
      }else{
        false
      }
    }).orNull
  }
}


