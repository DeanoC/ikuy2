package ikuy.hardware
import ikuy.common._

sealed trait HardChip

class HardRamChip(
    override val ident : String,
    override val size: String
) extends Chip with HardChip with RamChip

class HardCpuClusterChip(
    override val ident : String,
    val coreCount: Int,
    val arch: String,
    val width: Int,
    val localRam: Option[String]
) extends Chip with HardChip with CpuClusterChip

class HardBusAggregatorChip(
    override val ident : String,
    val primaryBus: String
) extends Chip with HardChip with BusAggregatorChip