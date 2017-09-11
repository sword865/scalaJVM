package com.sword865.scalaJVM.classfile.constantInfos

import com.sword865.scalaJVM.classfile.{ClassReader, ConstantInfo}

/**
  * Created by tianhaowei on 2017/9/7.
  */

object ConstantIntegerInfo {
  def apply(reader: ClassReader): ConstantIntegerInfo = {
    new ConstantIntegerInfo(reader.readInt())
  }
}


class ConstantIntegerInfo(val value: Int)  extends ConstantInfo
