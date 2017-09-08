package com.sword865.scalaJVM.classpath

import java.io.File

/**
  * Created by admin on 17/9/6.
  */

object CompositeEntry{
  def apply(path: String): CompositeEntry = {
    val entries = path.split(Entry.pathListSeparator).map(Entry.newEntry)
    new CompositeEntry(entries)
  }
}


case class CompositeEntry(entries: Array[_ <: Entry]) extends Entry{
  override def readClass(className: String): Option[Array[Byte]] = {
    entries.map(_.readClass(className)).find(_.nonEmpty).map(_.get)
  }

  override def toString: String = {
    s"CompositeEntry[${entries.map(_.toString).mkString(Entry.pathListSeparator)}]"
  }
}
