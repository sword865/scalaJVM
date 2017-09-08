package com.sword865.scalaJVM.classfile.attributeInfos

import com.sword865.scalaJVM.classfile.{AttributeInfo, ClassReader}

/**
  * Created by tianhaowei on 2017/9/7.
  */

object DeprecatedAttribute{
  def apply(reader: ClassReader): DeprecatedAttribute = new DeprecatedAttribute(new MarkerAttribute)
}

class DeprecatedAttribute(markerAttribute: MarkerAttribute) extends AttributeInfo


object SyntheticAttribute{
  def apply(reader: ClassReader): SyntheticAttribute = new SyntheticAttribute(new MarkerAttribute)
}

class SyntheticAttribute(markerAttribute: MarkerAttribute) extends AttributeInfo

class MarkerAttribute extends AttributeInfo