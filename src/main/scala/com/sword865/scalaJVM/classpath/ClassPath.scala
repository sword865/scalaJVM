package com.sword865.scalaJVM.classpath

import java.io.File

/**
  * Created by admin on 17/9/6.
  */

object ClassPath{
  def parse(jreOption: String, cpOption: String): ClassPath = {
    val (bootClassPath, extClassPath) = parseBootAndExtClassPath(jreOption)
    val userClassPath = parseUserClassPath(cpOption)
    ClassPath(bootClassPath, extClassPath, userClassPath)
  }

  def parseBootAndExtClassPath(jreOption: String): (Entry, Entry) = {
    val jreDir = getJreDir(jreOption)
    val jreLibPath = new File(jreDir, "lib/*").getPath
    val bootClassPath = WildcardEntry(jreLibPath)
    val jreExtPath = new File(jreDir, "lib/ext/*").getPath
    val extClassPath = WildcardEntry(jreLibPath)
    (bootClassPath, extClassPath)
  }

  def getJreDir(jreOption: String): String = {
    val systemJre = new File(System.getenv("JAVA_HOME"), "jre").getPath
    Array[String](jreOption, "./jre", systemJre).filter(exists).head
  }

  def exists(path: String): Boolean = {
    if(path.nonEmpty){
      new File(path).exists()
    }else{
      false
    }
  }

  def parseUserClassPath(cpOption: String): Entry = {
    if(cpOption.nonEmpty){
      Entry.newEntry(cpOption)
    }else{
      Entry.newEntry(".")
    }

  }
}

case class ClassPath(bootClasspath: Entry, extClasspath: Entry, userClasspath: Entry){
  def readClass(className: String): Option[(Entry, Array[Byte])] = {
    val  classFileName = className + ".class"
    val result = Array[Entry](bootClasspath, extClasspath, userClasspath).toStream.map(entry => (entry, entry.readClass(classFileName))).find(_._2.nonEmpty)
    if(result.nonEmpty){
      result.map(x=> (x._1, x._2.get))
    }else{
      Option.empty
    }
  }

  override def toString: String = s"ClassPath with userClassPath(${userClasspath.toString})"
}
