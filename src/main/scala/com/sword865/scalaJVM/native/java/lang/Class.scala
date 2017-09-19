package com.sword865.scalaJVM.native.java.lang

/**
  * Created by tianhaowei on 2017/9/19.
  */
import com.sword865.scalaJVM.rtda.heap
import com.sword865.scalaJVM.{native, rtda}

object Class {

  val jlClass = "java/lang/Class"

  def init() {
    native.register(jlClass, "getPrimitiveClass", "(Ljava/lang/String;)Ljava/lang/Class;", getPrimitiveClass)
    native.register(jlClass, "getName0", "()Ljava/lang/String;", getName0)
    native.register(jlClass, "desiredAssertionStatus0", "(Ljava/lang/Class;)Z", desiredAssertionStatus0)
    native.register(jlClass, "getComponentType", "()Ljava/lang/Class;", getComponentType)
  }
  // static native Class<?> getPrimitiveClass(String name);
  // (Ljava/lang/String;)Ljava/lang/Class;
  def getPrimitiveClass(frame: rtda.Frame): Unit ={
    val namObj = frame.localVars.getRef(0)
    val name = heap.StringPool.getString(namObj)

    val loader = frame.method.classStruct.loader
    val classStruct = loader.loadClass(name).jClass

    frame.operandStack.pushRef(classStruct)
  }

  // private native String getName0();
  // ()Ljava/lang/String;
  def getName0(frame: rtda.Frame): Unit ={
    val thisClass = frame.localVars.getThis
    val classStruct = thisClass.extra.asInstanceOf[heap.ClassStruct]

    val name = classStruct.javaName
    val nameObj = heap.StringPool.JString(classStruct.loader, name)

    frame.operandStack.pushRef(nameObj)
  }

  // public native Class<?> getComponentType();
  // ()Ljava/lang/Class;
  def getComponentType(frame: rtda.Frame): Unit ={
    val vars = frame.localVars
    val thisClass = vars.getThis
    val classStuct = thisClass.extra.asInstanceOf[heap.ClassStruct]
    val componentClass = classStuct.componentClass()
    val componentClassObj = componentClass.jClass

    val stack = frame.operandStack
    stack.pushRef(componentClassObj)
  }

  // private static native boolean desiredAssertionStatus0(Class<?> clazz);
  // (Ljava/lang/Class;)Z
  def desiredAssertionStatus0(frame: rtda.Frame): Unit ={
    //TODO
    frame.operandStack.pushBoolean(false)
  }

  // public native boolean isInterface();
  // ()Z
  def isInterface(frame: rtda.Frame): Unit ={
    val vars = frame.localVars
    val thisClass = vars.getThis
    val classStruct = thisClass.extra.asInstanceOf[heap.ClassStruct]

    val stack = frame.operandStack
    stack.pushBoolean(classStruct.isInterface)
  }

  // public native boolean isPrimitive();
  // ()Z
  def isPrimitive(frame: rtda.Frame): Unit ={
    val vars = frame.localVars
    val thisClass = vars.getThis
    val classStruct = thisClass.extra.asInstanceOf[heap.ClassStruct]

    val stack = frame.operandStack
    stack.pushBoolean(classStruct.isPrimitive)
  }
}
