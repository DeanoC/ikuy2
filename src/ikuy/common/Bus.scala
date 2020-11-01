package ikuy.common

import scala.collection.mutable.ArrayBuffer

trait BusEnd {
  val ident : String
}

trait Bus extends Feature {
  var end0: Option[BusEnd] = None
  var end1: Option[BusEnd] = None
}

case class InterconnectBusEnd(override val ident : String) extends BusEnd

case class InternalBus(override val ident: String) extends Bus {
}

case class Axi3Bus(override val ident: String) extends Bus
case class Axi4Bus(override val ident: String) extends Bus
