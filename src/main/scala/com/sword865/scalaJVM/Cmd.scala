package com.sword865.scalaJVM

import java.text.ParseException
import org.apache.commons.cli.{DefaultParser, HelpFormatter, Option => Opt, Options => Opts}


/**
  * Created by tianhaowei on 2017/9/5.
  */

case class Cmd(help: Boolean, version: Boolean, verboseClass: Boolean, verboseInst: Boolean,
               cpOption: String, className: String, args: Array[String], xJreOption: String)


object Cmd {

  def parseCmd(args: Array[String]): Option[Cmd] ={
    val options = new Opts()
    options.addOption(new Opt("?", "help", false, "print help message"))
    options.addOption(new Opt("v", "version", false, "print version and exit"))
    options.addOption(new Opt("vb", "verbose", false, "enable verbose output"))
    options.addOption(new Opt("vbc", "verbose:class", false, "enable verbose output"))
    options.addOption(new Opt("vbi", "verbose:inst", false, "enable verbose output"))
    options.addOption(new Opt("cp", "classpath", true, "classpath"))
    options.addOption(new Opt("j", "Xjre", true, "path to jre"))
    val parser = new DefaultParser()
    try {
      val cmd = parser.parse(options, args)
      val help = cmd.hasOption("help")
      val version = cmd.hasOption("version")
      val verboseClass = cmd.hasOption("verbose") || cmd.hasOption("verbose:class")
      val verboseInst = cmd.hasOption("verbose:inst")
      val classPath = cmd.getOptionValue("classpath", "")
      val xJre = cmd.getOptionValue("Xjre", "")

      val otherArgs: Array[String] = cmd.getArgList.toArray(Array[String]())
      val (cpOption, classArgs) = if(otherArgs.isEmpty){
        ("", Array[String]())
      }else{
        (otherArgs.head, otherArgs.tail)
      }
      Some(Cmd(help, version, verboseClass, verboseInst, classPath, cpOption, classArgs, xJre))
    }catch {
      case _: ParseException =>
        println("wrong arguments")
        None
    }
  }

  def printUsage(): Unit = {
    println("Usage: java [-options] class [args...]\n")
  }

}
