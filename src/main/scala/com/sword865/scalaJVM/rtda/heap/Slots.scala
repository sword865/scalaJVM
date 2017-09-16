package com.sword865.scalaJVM.rtda.heap

object Slots{
  def apply(slotCount: Int): Slots = new Slots(new Array[Any](slotCount))
}

