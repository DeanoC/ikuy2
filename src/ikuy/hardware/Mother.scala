package ikuy

import ikuy.hardware._
import scala.collection.immutable.HashMap

/*
import spinal.core._

class PWM(width : Int) extends Component {
  var io = new Bundle {
    // duty_cycle is the threshold value where the bit is on
    // UInt(width-1) = 100%
    // UInt(0) = 0%   
    val duty_cycle = in UInt(width bits)
    val pwm_out = out Bool
  }
  val counter = Reg(UInt(width bits))
  counter := (counter + 1).resize(width)
  when(io.duty_cycle > counter) {
    io.pwm_out := True
  }.otherwise
  {
    io.pwm_out := False
  }
}

class Mother( val board : Board )
{
  var ramChips = HashMap[String, RamChip]()
  var cpuClusterChips = HashMap[String, CpuClusterChip]()
  var systemOfChips = HashMap[String, SystemOnChip]()
  var otherChips = HashMap[String, Chip]()

  var buses = HashMap[String, Bus]()
  var interconnects = HashMap[String, Interconnect]()

  private def matchChips(chip : Chip) : Unit = {
    chip match {
      case cc: CpuClusterChip => cpuClusterChips += (cc.chipName -> cc)
      case rc: RamChip => ramChips += (rc.chipName -> rc)
      case soc: SystemOnChip => systemOfChips +=(soc.chipName -> soc)
      case other => otherChips += (other.chipName -> other)
    }
  } 

  def hasChip(name : String ) : Boolean = ramChips.contains(name) || cpuClusterChips.contains(name) || systemOfChips.contains(name) || otherChips.contains(name)

  def getChip(name : String) : Chip = {
    assert(hasChip(name))

    if(ramChips.contains(name)) ramChips(name)
    else if(cpuClusterChips.contains(name)) cpuClusterChips(name)
    else if(systemOfChips.contains(name)) systemOfChips(name)
    else if(otherChips.contains(name)) otherChips(name)
    else null // should never happen
  }  

  for(chip <- board.chips) matchChips(chip)

  for( (_, soc) <- systemOfChips) {
    for(chip <- soc.chips) matchChips(chip)
    for(bus <- soc.buses) buses += (bus.featureName -> bus)
    for(ic <- soc.interconnects) interconnects += (ic.featureName -> ic)
  }

  for { 
    (icName, interconnect) <- interconnects
    connectionName <- interconnect.connections 
    } {
      val splitName = connectionName.split('.')
      if(splitName.length != 2) {
        println(s"$connectionName isn't a valid bus or ram name")
      } else {
        splitName(0) match {
          case "buses" => if(buses.contains(splitName(1)))  buses(splitName(1)).end0 = Some(InterconnectBusEnd(icName))
          case "ram" => if(ramChips.contains(splitName(1)))  ramChips(splitName(1)).busEnd = Some(InterconnectBusEnd(icName))
          case _ => println(s"${splitName(0)} is an unknown connection type")
        }
      }
    }

  // start generating hardware
  for(cpuCluster <- cpuClusterChips) {

  }
}
*/

class Mother( val board : Board )
