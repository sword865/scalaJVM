package com.sword865.scalaJVM.classfile.constantInfos

import java.io.UTFDataFormatException

import com.sword865.scalaJVM.classfile.{ClassReader, ConstantInfo}
/**
  * Created by tianhaowei on 2017/9/7.
  */
object ConstantUtf8Info {
  def apply(reader: ClassReader): ConstantUtf8Info = {
    val length = reader.readUInt16()
    val bytes = reader.readBytes(length)
    new ConstantUtf8Info(decodeMUTF8(bytes))
  }

  def decodeMUTF8(bytearr: Array[Byte]): String = {
    val utflen = bytearr.length
    var c = 0
    var char2 = 0
    var char3 = 0
    var count = 0
    var chararr_count = 0
    val chararr = new Array[Char](utflen)

    while (count < utflen && c < 127) {
      c = bytearr(count).toInt & 0xff
      if (c < 127) {
        count += 1
        chararr(chararr_count) = c.toChar
        chararr_count += 1
      }
    }
    while (count < utflen) {
      c = bytearr(count).toInt & 0xff
      c >> 4 match {
        case 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 =>
          count += 1
          chararr(chararr_count) = c.toChar
          chararr_count += 1
        case 12 | 13 =>
          count += 2
          if (count > utflen) throw new UTFDataFormatException("malformed input: partial character at end")
          char2 = bytearr(count - 1).toInt
          if ((char2 & 0xC0) != 0x80) throw new UTFDataFormatException("malformed input around byte " + count)
          chararr(chararr_count) = (((c & 0x1F) << 6) | (char2 & 0x3F)).toChar
          chararr_count += 1
        case 14 =>
          count += 3
          if (count > utflen) throw new UTFDataFormatException("malformed input: partial character at end")
          char2 = bytearr(count - 2).toInt
          char3 = bytearr(count - 1).toInt
          if (((char2 & 0xC0) != 0x80) || ((char3 & 0xC0) != 0x80)) throw new UTFDataFormatException("malformed input around byte " + (count - 1))
          chararr(chararr_count) = (((c & 0x0F) << 12) | ((char2 & 0x3F) << 6) | ((char3 & 0x3F) << 0)).toChar
          chararr_count += 1;
        case _ =>
          throw new UTFDataFormatException("malformed input around byte " + count)
      }
    }
    new String(chararr, 0, chararr_count)
  }
}

class ConstantUtf8Info(val str: String) extends ConstantInfo
