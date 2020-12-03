package day3

class SledSlope {
    /**
     * You start on the open square (.) in the top-left corner and need to reach the bottom (below the bottom-most row on your map).

    The toboggan can only follow a few specific slopes (you opted for a cheaper model that prefers rational numbers); start by counting all the trees you would encounter for the slope right 3, down 1:

    From your starting position at the top-left, check the position that is right 3 and down 1. Then, check the position that is right 3 and down 1 from there, and so on until you go past the bottom of the map.

    Starting at the top-left corner of your map and following a slope of right 3 and down 1, how many trees would you encounter?
     */
    fun sledMapTreeCount(slopeMap: List<String>, rightProgress: Int, downProgress: Int): Int {
        val rowLength = slopeMap.first().length
        var treeCount = 0
        var currX = rightProgress
        var currY = downProgress
        val slopePath = StringBuffer()
        while (currY < slopeMap.size) {
            val slopeRow = slopeMap[currY]
            if (slopeRow[currX] == '#') {
                treeCount++
                slopePath.append('X')
            } else {
                slopePath.append('O')
            }
            currY += downProgress
            currX = (currX + rightProgress) % rowLength
        }
        println("Slope Path: $slopePath")
        return treeCount
    }
}

/**
 *
    Right 1, down 1.
    Right 3, down 1. (This is the slope you already checked.)
    Right 5, down 1.
    Right 7, down 1.
    Right 1, down 2.
    return Multiplied tree count
 */
fun main() {
    val slopeMap = FileHelpers.readFileLineByLineUsingForEachLine("./src/day3/day3SlopeMap.txt")
    val sledSlope = SledSlope()
    val treeCount1x1 = sledSlope.sledMapTreeCount(slopeMap, 1, 1)
    println("Tree count 1x1: $treeCount1x1")
    val treeCount3x1 = sledSlope.sledMapTreeCount(slopeMap, 3, 1)
    println("Tree count 3x1: $treeCount3x1")
    val treeCount5x1 = sledSlope.sledMapTreeCount(slopeMap, 5, 1)
    println("Tree count 5x1: $treeCount5x1")
    val treeCount7x1 = sledSlope.sledMapTreeCount(slopeMap, 7, 1)
    println("Tree count 7x1: $treeCount7x1")
    val treeCount1x2 = sledSlope.sledMapTreeCount(slopeMap, 1, 2)
    println("Tree count 1x2: $treeCount1x2")

    val treeMultiplied = treeCount1x1 * treeCount3x1 * treeCount5x1 * treeCount7x1 * treeCount1x2
    println("Tree multiplied = $treeMultiplied")
}