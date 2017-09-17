package com.sword865.scalaJVM.rtda.heap

import scala.collection.mutable.ArrayBuffer


class MethodDescriptor {
  val parameterTypes: ArrayBuffer[String] = ArrayBuffer[String]()
  var returnType: String = _

  def addParameterType(t: String): Unit ={
    parameterTypes.append(t)
  }

}
