package ikuy.hardware

import ikuy.common._

sealed trait XiConstraintBufferType
object XiC_NoBuffer extends XiConstraintBufferType
object XiC_InputBuffer extends XiConstraintBufferType { override def toString() = "IBUF" }
object XiC_OutputBuffer extends XiConstraintBufferType { override def toString() = "OBUF" }
object XiC_BiBuffer extends XiConstraintBufferType { override def toString() = "BIBUF" }

sealed trait XiConstraintIoStandard
object XiC_LVCMOS18 extends XiConstraintIoStandard { override def toString(): String = "LVCMOS18" }
object XiC_LVCMOS33 extends XiConstraintIoStandard { override def toString(): String = "LVCMOS33" }
object XiC_SSTL15 extends XiConstraintIoStandard { override def toString(): String = "SSTL15" }
object XiC_SSTL15_T_DCI extends XiConstraintIoStandard { override def toString(): String = "SSTL15_T_DCI" }
object XiC_DIFF_SSTL15_T_DCI extends XiConstraintIoStandard { override def toString(): String = "DIFF_SSTL15_T_DCI" }
object XiC_TDMS_33 extends XiConstraintIoStandard { override def toString(): String = "TDMS_33" }

sealed trait XiConstraintSlew
object XiC_NoSlew extends XiConstraintSlew
object XiC_SlowSlew extends XiConstraintSlew { override def toString(): String = "SLOW" }
object XiC_FastSlew extends XiConstraintSlew { override def toString(): String = "FAST" }

sealed trait XiConstraintDirection
object XiC_NoDirection extends XiConstraintDirection
object XiC_InputDirection extends XiConstraintDirection { override def toString(): String = "INPUT" }
object XiC_OutputDirection extends XiConstraintDirection { override def toString(): String = "OUTPUT" }
object XiC_BiDirection extends XiConstraintDirection { override def toString(): String = "BIDIR" }


class XiConstraint( val ident : String, 
                    val pin : String,
                    val ioStandard : XiConstraintIoStandard = XiC_LVCMOS33,
                    val buffering : XiConstraintBufferType = XiC_NoBuffer,
                    val slew : XiConstraintSlew = XiC_NoSlew,
                    val direction : XiConstraintDirection = XiC_NoDirection,
                    val pullup : Boolean = false,
                    val drive : Int = 0 )
extends Feature with HardConstraint
{
  override def toString(): String = {
      var s : StringBuilder = new StringBuilder()
      s ++= s"set_property -dict { "
      s ++= s"""PACKAGE_PIN ${pin} """
      s ++= s"""IOSTANDARD "${ioStandard}" """
      if(buffering != XiC_NoBuffer) s ++= s"""IO_BUFFER_TYPE "${buffering}" """
      if(slew != XiC_NoSlew) s ++= s"""SLEW "${slew}" """
      if(direction != XiC_NoDirection) s ++= s"""PIO_DIRECTION "${direction}" """
      if(pullup) s++= """PULLUP "TRUE" """
      if(drive > 0) s++= s"""DRIVE "${drive}" """

      s ++= s"""} [get_ports { "${ident}" } }];\n"""
      s.result
  }
}

class XiArrayConstraint( val ident : String, 
                        val pins : Array[String],
                        val ioStandard : XiConstraintIoStandard = XiC_LVCMOS33,
                        val buffering : XiConstraintBufferType = XiC_NoBuffer,
                        val slew :XiConstraintSlew = XiC_NoSlew,
                        val direction : XiConstraintDirection = XiC_NoDirection,
                        val pullup : Boolean = false,
                        val drive : Int = 0,
                        val pinNames : Array[String] = Array[String](), 
                        val directions : Array[XiConstraintDirection] = Array[XiConstraintDirection](),
                        val pullups : Array[Boolean] = Array[Boolean]() )
extends Feature with HardConstraint
{

  override def toString(): String = { 
    var s : StringBuilder = new StringBuilder()

    if(directions.nonEmpty) assert(pins.length == directions.length)
    if(pullups.nonEmpty) assert(pins.length == pullups.length)
    if(pinNames.nonEmpty) assert(pins.length == pinNames.length)

    for (i <- 0 until pins.length) {
      val dir = if(directions.isEmpty) direction else directions(i)
      val pu = if(pullups.isEmpty) pullup else pullups(i)
      val n = if(pinNames.isEmpty) s"$ident[$i]" else s"${ident}_${pinNames(i)}"

      s ++= (new XiConstraint(  n, s"${pins(i)}", 
                                ioStandard, 
                                buffering, 
                                slew,
                                dir,
                                pu, 
                                drive)).toString
    }
    s.result
  }
}


class XiDiffConstraint( val ident : String, 
                        val pins : Array[String],
                        val ioStandard : XiConstraintIoStandard = XiC_LVCMOS33,
                        val buffering : XiConstraintBufferType = XiC_NoBuffer,
                        val slew :XiConstraintSlew = XiC_NoSlew,
                        val direction : XiConstraintDirection = XiC_NoDirection,
                        val pullup : Boolean = false,
                        val drive : Int = 0 )
extends Feature with HardConstraint
{

  override def toString(): String = { 
    var s : StringBuilder = new StringBuilder()
    assert(pins.length == 2)
    val difText = Array("_p", "_n")

    for (i <- 0 until 2) {
      s ++= (new XiConstraint(s"${ident}${difText(i)}", s"${pins(i)}", 
                                ioStandard, 
                                buffering, 
                                slew,
                                direction,
                                pullup, 
                                drive)).toString
    }
    s.result
  }
}

case class XiPin( override val ident : String, 
                  override val pin : String,
                  override val ioStandard : XiConstraintIoStandard = XiC_LVCMOS33,
                  override val buffering : XiConstraintBufferType = XiC_NoBuffer,
                  override val slew :XiConstraintSlew = XiC_NoSlew,
                  override val direction : XiConstraintDirection = XiC_NoDirection,
                  override val pullup : Boolean = false,
                  override val drive : Int = 0)
extends XiConstraint(ident, pin, ioStandard, buffering, slew, direction, pullup, drive)

case class XiDiffPin( override val ident : String, 
                      override val pins : Array[String],
                      override val ioStandard : XiConstraintIoStandard = XiC_LVCMOS33,
                      override val buffering : XiConstraintBufferType = XiC_NoBuffer,
                      override val slew :XiConstraintSlew = XiC_NoSlew,
                      override val direction : XiConstraintDirection = XiC_NoDirection,
                      override val pullup : Boolean = false,
                      override val drive : Int = 0)
extends XiDiffConstraint(ident, pins, ioStandard, buffering, slew, direction, pullup, drive)

case class XiPinArray(override val ident : String,
                      override val pins : Array[String],
                      override val ioStandard : XiConstraintIoStandard = XiC_LVCMOS33,
                      override val buffering : XiConstraintBufferType = XiC_NoBuffer,
                      override val slew :XiConstraintSlew = XiC_NoSlew,
                      override val direction : XiConstraintDirection = XiC_NoDirection,
                      override val pullup : Boolean = false,
                      override val drive : Int = 0,
                      override val pinNames : Array[String] = Array[String](), 
                      override val directions : Array[XiConstraintDirection] = Array[XiConstraintDirection](),
                      override val pullups : Array[Boolean] = Array[Boolean]() )
extends XiArrayConstraint(ident, pins, ioStandard, buffering, slew, direction, pullup, drive, pinNames, directions, pullups)
