package ikuy.common

trait ChipType
trait RamChip extends ChipType {
  val size : String
}
trait CpuClusterChip extends ChipType
trait BusAggregatorChip extends ChipType

trait Chip extends Feature {
  override val ident : String
}


