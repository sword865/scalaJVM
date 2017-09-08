package com.sword865.scalaJVM.classpath

/**
  * Created by admin on 17/9/6.
  */
import java.io.File

object WildcardEntry{

  def listFiles(root: File): Array[File] = {
    root.listFiles().flatMap({
      f =>
        if(f.isDirectory){
          listFiles(f)
        }else{
          Array[File](f)
        }
    })
  }

  def apply(path: String): CompositeEntry = {
    val baseDir = new File(path.dropRight(1))
    val entries = baseDir.listFiles().filter(f => !f.isDirectory && f.getName.toLowerCase.endsWith("jar")).map({
      f=>
        ZipEntry(f.getAbsolutePath)
    })
    new CompositeEntry(entries)
  }
}