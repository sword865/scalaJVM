package com.sword865.scalaJVM.javafile

class ShTest {

  def main(args: Array[String]): Unit = {
    val x = -1
    System.out.println(Integer.toBinaryString(x))
    System.out.println(Integer.toBinaryString(x >> 8))
    System.out.println(Integer.toBinaryString(x >>> 8))
  }

}
