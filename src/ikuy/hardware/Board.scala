package ikuy.hardware

import ikuy.common._

class Board(
    val name: String,
    val chips: Array[Chip],
    val constraints: Array[Constraint]
) {}
