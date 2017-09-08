package com.sword865.scalaJVM.classpath

import java.io.File
import java.nio.file.{Files, Paths}
/**
  * Created by admin on 17/9/6.
  */

object DirEntry{
  def apply(path: String): DirEntry = {
    val absPath = new File(path).getAbsolutePath
    new DirEntry(absPath)
  }
}

case class DirEntry(path: String) extends Entry {
  override def readClass(className: String): Option[Array[Byte]] = {
    try {
      val file = new File(path, className).getAbsolutePath
      val data = Files.readAllBytes(Paths.get(file))
      Some(data)
    }catch {
      case _: Throwable => Option.empty
    }
  }
}
