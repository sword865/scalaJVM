package com.sword865.scalaJVM.classfile.constantInfos

import com.sword865.scalaJVM.classfile.{ClassReader, ConstantInfo}

/**
  * Created by tianhaowei on 2017/9/7.
  */

object ConstantLongInfo{
  def apply(reader: ClassReader): ConstantLongInfo = {
    new ConstantLongInfo(reader.readLong())
  }
}


class ConstantLongInfo(value: Long) extends ConstantInfo