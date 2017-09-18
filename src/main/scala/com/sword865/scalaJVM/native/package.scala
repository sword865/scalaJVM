package com.sword865.scalaJVM

import scala.collection.mutable.{Map=>MMap}
/**
  * Created by tianhaowei on 2017/9/19.
  */
package object native {
  type  NativeMethod = (rtda.Frame => Unit)

  var registry: MMap[String, NativeMethod] = MMap[String, NativeMethod]()

  def emptyNativeMethod: NativeMethod = {}

  def register(className: String, methodName: String, methodDescriptor: String, method: NativeMethod): Unit = {
    val key =  s"$className~$methodName~$methodDescriptor"
    registry(key) = method
  }

  def findNativeMethod(className: String, methodName: String, methodDescriptor: String): NativeMethod = {
    val key =  s"$className~$methodName~$methodDescriptor"
    val method = registry.get(key)
    if(method.isDefined){
      method.get
    }else{
      if(methodDescriptor == "()V" && methodName == "registerNatives"){
        emptyNativeMethod
      }else{
        null
      }
    }
  }

}
