package com.sword865.scalaJVM.instructions

import com.sword865.scalaJVM.instructions.base.Instruction
import com.sword865.scalaJVM.instructions.constants.NOP
import com.sword865.scalaJVM.instructions.constants.CONST._
import com.sword865.scalaJVM.instructions.constants.IPUSH._
import com.sword865.scalaJVM.instructions.loads.LOAD._
import com.sword865.scalaJVM.instructions.loads.LOAD_N._
import com.sword865.scalaJVM.instructions.stores.STORE._
import com.sword865.scalaJVM.instructions.stores.STORE_N._
import com.sword865.scalaJVM.instructions.comparisons.IFCOND._
import com.sword865.scalaJVM.instructions.comparisons.IF_CMP._
import com.sword865.scalaJVM.instructions.comparisons.CMP._
import com.sword865.scalaJVM.instructions.control._
import com.sword865.scalaJVM.instructions.conversions.N2X._
import com.sword865.scalaJVM.instructions.extended.{WIDE,GOTO_W}
import com.sword865.scalaJVM.instructions.extended.IFYNNULL._
import com.sword865.scalaJVM.instructions.math.IINC
import com.sword865.scalaJVM.instructions.math.ADD._
import com.sword865.scalaJVM.instructions.math.DIV._
import com.sword865.scalaJVM.instructions.math.MUL._
import com.sword865.scalaJVM.instructions.math.SUB._
import com.sword865.scalaJVM.instructions.math.REM._
import com.sword865.scalaJVM.instructions.math.NEG._
import com.sword865.scalaJVM.instructions.math.BOOL2._
import com.sword865.scalaJVM.instructions.math.SH._
import com.sword865.scalaJVM.instructions.stack.DUP_N_X._
import com.sword865.scalaJVM.instructions.stack.POP_N._
import com.sword865.scalaJVM.instructions.stack.SWAP

object InstructionFactory {

  val nop         = new NOP()
  val aconst_null = new ACONST_NULL()
  val iconst_m1   = new ICONST_M1()
  val iconst_0    = new ICONST_0()
  val iconst_1    = new ICONST_1()
  val iconst_2    = new ICONST_2()
  val iconst_3    = new ICONST_3()
  val iconst_4    = new ICONST_4()
  val iconst_5    = new ICONST_5()
  val lconst_0    = new LCONST_0()
  val lconst_1    = new LCONST_1()
  val fconst_0    = new FCONST_0()
  val fconst_1    = new FCONST_1()
  val fconst_2    = new FCONST_2()
  val dconst_0    = new DCONST_0()
  val dconst_1    = new DCONST_1()
  val iload_0     = new ILOAD_0()
  val iload_1     = new ILOAD_1()
  val iload_2     = new ILOAD_2()
  val iload_3     = new ILOAD_3()
  val lload_0     = new LLOAD_0()
  val lload_1     = new LLOAD_1()
  val lload_2     = new LLOAD_2()
  val lload_3     = new LLOAD_3()
  val fload_0     = new FLOAD_0()
  val fload_1     = new FLOAD_1()
  val fload_2     = new FLOAD_2()
  val fload_3     = new FLOAD_3()
  val dload_0     = new DLOAD_0()
  val dload_1     = new DLOAD_1()
  val dload_2     = new DLOAD_2()
  val dload_3     = new DLOAD_3()
  val aload_0     = new ALOAD_0()
  val aload_1     = new ALOAD_1()
  val aload_2     = new ALOAD_2()
  val aload_3     = new ALOAD_3()
  // val iaload      = new IALOAD()
  // val laload      = new LALOAD()
  // val faload      = new FALOAD()
  // val daload      = new DALOAD()
  // val aaload      = new AALOAD()
  // val baload      = new BALOAD()
  // val caload      = new CALOAD()
  // val saload      = new SALOAD()
  val istore_0 = new ISTORE_0()
  val istore_1 = new ISTORE_1()
  val istore_2 = new ISTORE_2()
  val istore_3 = new ISTORE_3()
  val lstore_0 = new LSTORE_0()
  val lstore_1 = new LSTORE_1()
  val lstore_2 = new LSTORE_2()
  val lstore_3 = new LSTORE_3()
  val fstore_0 = new FSTORE_0()
  val fstore_1 = new FSTORE_1()
  val fstore_2 = new FSTORE_2()
  val fstore_3 = new FSTORE_3()
  val dstore_0 = new DSTORE_0()
  val dstore_1 = new DSTORE_1()
  val dstore_2 = new DSTORE_2()
  val dstore_3 = new DSTORE_3()
  val astore_0 = new ASTORE_0()
  val astore_1 = new ASTORE_1()
  val astore_2 = new ASTORE_2()
  val astore_3 = new ASTORE_3()
  // val iastore  = new IASTORE()
  // val lastore  = new LASTORE()
  // val fastore  = new FASTORE()
  // val dastore  = new DASTORE()
  // val aastore  = new AASTORE()
  // val bastore  = new BASTORE()
  // val castore  = new CASTORE()
  // val sastore  = new SASTORE()
  val pop     = new POP()
  val pop2    = new POP2()
  val dup     = new DUP()
  val dup_x1  = new DUP_X1()
  val dup_x2  = new DUP_X2()
  val dup2    = new DUP2()
  val dup2_x1 = new DUP2_X1()
  val dup2_x2 = new DUP2_X2()
  val swap    = new SWAP()
  val iadd    = new IADD()
  val ladd    = new LADD()
  val fadd    = new FADD()
  val dadd    = new DADD()
  val isub    = new ISUB()
  val lsub    = new LSUB()
  val fsub    = new FSUB()
  val dsub    = new DSUB()
  val imul    = new IMUL()
  val lmul    = new LMUL()
  val fmul    = new FMUL()
  val dmul    = new DMUL()
  val idiv    = new IDIV()
  val ldiv    = new LDIV()
  val fdiv    = new FDIV()
  val ddiv    = new DDIV()
  val irem    = new IREM()
  val lrem    = new LREM()
  val frem    = new FREM()
  val drem    = new DREM()
  val ineg    = new INEG()
  val lneg    = new LNEG()
  val fneg    = new FNEG()
  val dneg    = new DNEG()
  val ishl    = new ISHL()
  val lshl    = new LSHL()
  val ishr    = new ISHR()
  val lshr    = new LSHR()
  val iushr   = new IUSHR()
  val lushr   = new LUSHR()
  val iand    = new IAND()
  val land    = new LAND()
  val ior     = new IOR()
  val lor     = new LOR()
  val ixor    = new IXOR()
  val lxor    = new LXOR()
  val i2l     = new I2L()
  val i2f     = new I2F()
  val i2d     = new I2D()
  val l2i     = new L2I()
  val l2f     = new L2F()
  val l2d     = new L2D()
  val f2i     = new F2I()
  val f2l     = new F2L()
  val f2d     = new F2D()
  val d2i     = new D2I()
  val d2l     = new D2L()
  val d2f     = new D2F()
  val i2b     = new I2B()
  val i2c     = new I2C()
  val i2s     = new I2S()
  val lcmp    = new LCMP()
  val fcmpl   = new FCMPL()
  val fcmpg   = new FCMPG()
  val dcmpl   = new DCMPL()
  val dcmpg   = new DCMPG()
  // val ireturn = new IRETURN()
  // val lreturn = new LRETURN()
  // val freturn = new FRETURN()
  // val dreturn = new DRETURN()
  // val areturn = new ARETURN()
  // val _return = new RETURN()
  // val arraylength   = new ARRAY_LENGTH()
  // val athrow        = new ATHROW()
  // val monitorenter  = new MONITOR_ENTER()
  // val monitorexit   = new MONITOR_EXIT()
  // val invoke_native = new INVOKE_NATIVE()

  def newInstruction(opcode: Short): Instruction = {
    opcode match {
      case 0x00 =>
        nop
      case 0x01 =>
        aconst_null
      case 0x02 =>
        iconst_m1
      case 0x03 =>
        iconst_0
      case 0x04 =>
        iconst_1
      case 0x05 =>
        iconst_2
      case 0x06 =>
        iconst_3
      case 0x07 =>
        iconst_4
      case 0x08 =>
        iconst_5
      case 0x09 =>
        lconst_0
      case 0x0a =>
        lconst_1
      case 0x0b =>
        fconst_0
      case 0x0c =>
        fconst_1
      case 0x0d =>
        fconst_2
      case 0x0e =>
        dconst_0
      case 0x0f =>
        dconst_1
      case 0x10 =>
        new BIPUSH()
      case 0x11 =>
        new SIPUSH()
      // case 0x12 =>
      // 	new LDC()
      // case 0x13 =>
      // 	new LDC_W()
      // case 0x14 =>
      // 	new LDC2_W()
      case 0x15 =>
        new ILOAD()
      case 0x16 =>
        new LLOAD()
      case 0x17 =>
        new FLOAD()
      case 0x18 =>
        new DLOAD()
      case 0x19 =>
        new ALOAD()
      case 0x1a =>
        iload_0
      case 0x1b =>
        iload_1
      case 0x1c =>
        iload_2
      case 0x1d =>
        iload_3
      case 0x1e =>
        lload_0
      case 0x1f =>
        lload_1
      case 0x20 =>
        lload_2
      case 0x21 =>
        lload_3
      case 0x22 =>
        fload_0
      case 0x23 =>
        fload_1
      case 0x24 =>
        fload_2
      case 0x25 =>
        fload_3
      case 0x26 =>
        dload_0
      case 0x27 =>
        dload_1
      case 0x28 =>
        dload_2
      case 0x29 =>
        dload_3
      case 0x2a =>
        aload_0
      case 0x2b =>
        aload_1
      case 0x2c =>
        aload_2
      case 0x2d =>
        aload_3
      // case 0x2e =>
      // 	iaload
      // case 0x2f =>
      // 	laload
      // case 0x30 =>
      // 	faload
      // case 0x31 =>
      // 	daload
      // case 0x32 =>
      // 	aaload
      // case 0x33 =>
      // 	baload
      // case 0x34 =>
      // 	caload
      // case 0x35 =>
      // 	saload
      case 0x36 =>
        new ISTORE()
      case 0x37 =>
        new LSTORE()
      case 0x38 =>
        new FSTORE()
      case 0x39 =>
        new DSTORE()
      case 0x3a =>
        new ASTORE()
      case 0x3b =>
        istore_0
      case 0x3c =>
        istore_1
      case 0x3d =>
        istore_2
      case 0x3e =>
        istore_3
      case 0x3f =>
        lstore_0
      case 0x40 =>
        lstore_1
      case 0x41 =>
        lstore_2
      case 0x42 =>
        lstore_3
      case 0x43 =>
        fstore_0
      case 0x44 =>
        fstore_1
      case 0x45 =>
        fstore_2
      case 0x46 =>
        fstore_3
      case 0x47 =>
        dstore_0
      case 0x48 =>
        dstore_1
      case 0x49 =>
        dstore_2
      case 0x4a =>
        dstore_3
      case 0x4b =>
        astore_0
      case 0x4c =>
        astore_1
      case 0x4d =>
        astore_2
      case 0x4e =>
        astore_3
      // case 0x4f =>
      // 	iastore
      // case 0x50 =>
      // 	lastore
      // case 0x51 =>
      // 	fastore
      // case 0x52 =>
      // 	dastore
      // case 0x53 =>
      // 	aastore
      // case 0x54 =>
      // 	bastore
      // case 0x55 =>
      // 	castore
      // case 0x56 =>
      // 	sastore
      case 0x57 =>
        pop
      case 0x58 =>
        pop2
      case 0x59 =>
        dup
      case 0x5a =>
        dup_x1
      case 0x5b =>
        dup_x2
      case 0x5c =>
        dup2
      case 0x5d =>
        dup2_x1
      case 0x5e =>
        dup2_x2
      case 0x5f =>
        swap
      case 0x60 =>
        iadd
      case 0x61 =>
        ladd
      case 0x62 =>
        fadd
      case 0x63 =>
        dadd
      case 0x64 =>
        isub
      case 0x65 =>
        lsub
      case 0x66 =>
        fsub
      case 0x67 =>
        dsub
      case 0x68 =>
        imul
      case 0x69 =>
        lmul
      case 0x6a =>
        fmul
      case 0x6b =>
        dmul
      case 0x6c =>
        idiv
      case 0x6d =>
        ldiv
      case 0x6e =>
        fdiv
      case 0x6f =>
        ddiv
      case 0x70 =>
        irem
      case 0x71 =>
        lrem
      case 0x72 =>
        frem
      case 0x73 =>
        drem
      case 0x74 =>
        ineg
      case 0x75 =>
        lneg
      case 0x76 =>
        fneg
      case 0x77 =>
        dneg
      case 0x78 =>
        ishl
      case 0x79 =>
        lshl
      case 0x7a =>
        ishr
      case 0x7b =>
        lshr
      case 0x7c =>
        iushr
      case 0x7d =>
        lushr
      case 0x7e =>
        iand
      case 0x7f =>
        land
      case 0x80 =>
        ior
      case 0x81 =>
        lor
      case 0x82 =>
        ixor
      case 0x83 =>
        lxor
      case 0x84 =>
        new IINC()
      case 0x85 =>
        i2l
      case 0x86 =>
        i2f
      case 0x87 =>
        i2d
      case 0x88 =>
        l2i
      case 0x89 =>
        l2f
      case 0x8a =>
        l2d
      case 0x8b =>
        f2i
      case 0x8c =>
        f2l
      case 0x8d =>
        f2d
      case 0x8e =>
        d2i
      case 0x8f =>
        d2l
      case 0x90 =>
        d2f
      case 0x91 =>
        i2b
      case 0x92 =>
        i2c
      case 0x93 =>
        i2s
      case 0x94 =>
        lcmp
      case 0x95 =>
        fcmpl
      case 0x96 =>
        fcmpg
      case 0x97 =>
        dcmpl
      case 0x98 =>
        dcmpg
      case 0x99 =>
        new IFEQ()
      case 0x9a =>
        new IFNE()
      case 0x9b =>
        new IFLT()
      case 0x9c =>
        new IFGE()
      case 0x9d =>
        new IFGT()
      case 0x9e =>
        new IFLE()
      case 0x9f =>
        new IF_ICMPEQ()
      case 0xa0 =>
        new IF_ICMPNE()
      case 0xa1 =>
        new IF_ICMPLT()
      case 0xa2 =>
        new IF_ICMPGE()
      case 0xa3 =>
        new IF_ICMPGT()
      case 0xa4 =>
        new IF_ICMPLE()
      case 0xa5 =>
        new IF_ACMPEQ()
      case 0xa6 =>
        new IF_ACMPNE()
      case 0xa7 =>
        new GOTO()
      // case 0xa8 =>
      // 	new JSR()
      // case 0xa9 =>
      // 	new RET()
      case 0xaa =>
        new TABLE_SWITCH()
      case 0xab =>
        new LOOKUP_SWITCH()
      // case 0xac =>
      // 	ireturn
      // case 0xad =>
      // 	lreturn
      // case 0xae =>
      // 	freturn
      // case 0xaf =>
      // 	dreturn
      // case 0xb0 =>
      // 	areturn
      // case 0xb1 =>
      // 	_return
      //	case 0xb2 =>
      //		new GET_STATIC()
      // case 0xb3 =>
      // 	new PUT_STATIC()
      // case 0xb4 =>
      // 	new GET_FIELD()
      // case 0xb5 =>
      // 	new PUT_FIELD()
      //	case 0xb6 =>
      //		new INVOKE_VIRTUAL()
      // case 0xb7 =>
      // 	new INVOKE_SPECIAL()
      // case 0xb8 =>
      // 	new INVOKE_STATIC()
      // case 0xb9 =>
      // 	new INVOKE_INTERFACE()
      // case 0xba =>
      // 	new INVOKE_DYNAMIC()
      // case 0xbb =>
      // 	new NEW()
      // case 0xbc =>
      // 	new NEW_ARRAY()
      // case 0xbd =>
      // 	new ANEW_ARRAY()
      // case 0xbe =>
      // 	arraylength
      // case 0xbf =>
      // 	athrow
      // case 0xc0 =>
      // 	new CHECK_CAST()
      // case 0xc1 =>
      // 	new INSTANCE_OF()
      // case 0xc2 =>
      // 	monitorenter
      // case 0xc3 =>
      // 	monitorexit
      case 0xc4 =>
        new WIDE()
      // case 0xc5 =>
      // 	new MULTI_ANEW_ARRAY()
      case 0xc6 =>
        new IFNULL()
      case 0xc7 =>
        new IFNONNULL()
      case 0xc8 =>
        new GOTO_W()
      // case 0xc9 =>
      // 	new JSR_W()
      // case 0xca => breakpoint
      // case 0xfe => impdep1
      // case 0xff => impdep2
      case _ =>
        throw new Exception(f"Unsupported opcode => 0x$opcode!")
    }
  }
}
