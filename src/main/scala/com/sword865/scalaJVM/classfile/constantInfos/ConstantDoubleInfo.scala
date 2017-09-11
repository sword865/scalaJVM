package com.sword865.scalaJVM.classfile.constantInfos

import com.sword865.scalaJVM.classfile.{ClassReader, ConstantInfo}

/**
  * Created by tianhaowei on 2017/9/7.
  */

object ConstantDoubleInfo{
  def apply(reader: ClassReader): ConstantDoubleInfo = {
    new ConstantDoubleInfo(reader.readDouble())
  }
}


class ConstantDoubleInfo(val value: Double)  extends ConstantInfo