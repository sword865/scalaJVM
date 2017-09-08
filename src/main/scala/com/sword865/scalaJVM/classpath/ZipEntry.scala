package com.sword865.scalaJVM.classpath

import java.io.File
import java.util.zip.{ZipFile, ZipEntry => JZipEntry}
/**
  * Created by admin on 17/9/6.
  */


object ZipEntry{
  def apply(path: String): ZipEntry = {
    val absPath = new File(path).getAbsolutePath
    new ZipEntry(absPath)
  }
}

case class ZipEntry(path: String) extends Entry {

  def zipIter(iter: java.util.Enumeration[_ <: JZipEntry]): Stream[JZipEntry] ={
    if(iter.hasMoreElements){
      Stream.cons(iter.nextElement(), zipIter(iter))
    }else{
      Stream.empty
    }
  }

  override def readClass(className: String): Option[Array[Byte]] = {
    val zipFile = new ZipFile(path)
    val iter = zipIter(zipFile.entries())
    val zipF = iter.filter(!_.isDirectory).map({
      zipF =>
        (zipF, zipF.getName)
    }).filter(_._2 == className).map(_._1).headOption
    if(zipF.isDefined){
      // println(s"read from ${path}: ${zipF.get.getName}")
      val is = zipFile.getInputStream(zipF.get)
      Some(Stream.continually(is.read()).takeWhile(_ != -1).map(_.toByte).toArray)
    }else{
      Option.empty
    }
  }
}
