package com.sword865.scalaJVM.classfile.constantInfos

import com.sword865.scalaJVM.classfile.{ClassReader, ConstantInfo}

/**
  * Created by tianhaowei on 2017/9/7.
  */

object ConstantFloatInfo{
  def apply(reader: ClassReader): ConstantFloatInfo = {
    new ConstantFloatInfo(reader.readFloat())
  }
}


class ConstantFloatInfo(value: Float)  extends ConstantInfo