package ikuy.common

trait Feature {
  val ident : String
  var used = false
  
  def use(): Unit = used = true
}
