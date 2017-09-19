package com.sword865.scalaJVM.rtda.heap

import com.sword865.scalaJVM.rtda.heap
import scala.collection.mutable.{Map=>MMap}

/**
  * Created by tianhaowei on 2017/9/18.
  */
object StringPool {
  var internedStrings:MMap[String, heap.Object] = MMap[String, heap.Object]()

  def internString(jStr: Object): heap.Object = {
    val str = getString(jStr)
    internedStrings.getOrElseUpdate(str, jStr)
  }


  def JString(loader: ClassLoader, str: String): heap.Object = {
    val internedStr = internedStrings.get(str)
    if(internedStr.isDefined){
      internedStr.get
    }else {
      val jChars = heap.Object(loader.loadClass("[C"), str.toCharArray)
      val jStr = loader.loadClass("java/lang/String").newObject()
      jStr.setRefVar("value", "[C", jChars)
      internedStrings(str) = jStr
      jStr
    }
  }

  def getString(jStr: heap.Object): String = {
    val charArr = jStr.getRefVar("value", "[C")
    new String(charArr.arr[Char])
  }

}
