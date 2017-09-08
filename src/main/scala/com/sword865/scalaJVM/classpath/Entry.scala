package com.sword865.scalaJVM.classpath

import java.io.File

/**
  * Created by admin on 17/9/6.
  */

object Entry {
  val pathListSeparator: String = File.pathSeparator

  def newEntry(path: String): Entry = {
    if (path.contains(pathListSeparator)) {
      CompositeEntry(path)
    }
    if (path.endsWith("*")) {
      WildcardEntry(path)
    }
    if (path.toLowerCase().endsWith(".jar") || path.toLowerCase.endsWith(".zip")) {
      ZipEntry(path)
    }
    DirEntry(path)
  }

}

abstract class Entry{
  def readClass(className: String): Option[Array[Byte]]
}
