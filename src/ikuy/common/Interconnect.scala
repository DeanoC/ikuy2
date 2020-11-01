package ikuy.common

trait Interconnect extends Feature {
  override val ident : String 
  val connections : List[String]
}
