package day5

import kotlin.math.max

class SeatFinder {
    /**
     * The first 7 characters will either be F or B; these specify exactly one of the 128 rows on the plane (numbered 0 through 127). Each letter tells you which half of a region the given seat is in. Start with the whole list of rows; the first letter indicates whether the seat is in the front (0 through 63) or the back (64 through 127). The next letter indicates which half of that region the seat is in, and so on until you're left with exactly one row.

    For example, consider just the first seven characters of FBFBBFFRLR:

    Start by considering the whole range, rows 0 through 127.
    F means to take the lower half, keeping rows 0 through 63.
    B means to take the upper half, keeping rows 32 through 63.
    F means to take the lower half, keeping rows 32 through 47.
    B means to take the upper half, keeping rows 40 through 47.
    B keeps rows 44 through 47.
    F keeps rows 44 through 45.
    The final F keeps the lower of the two, row 44.
    The last three characters will be either L or R; these specify exactly one of the 8 columns of seats on the plane (numbered 0 through 7). The same process as above proceeds again, this time with only three steps. L means to keep the lower half, while R means to keep the upper half.

    For example, consider just the last 3 characters of FBFBBFFRLR:

    Start by considering the whole range, columns 0 through 7.
    R means to take the upper half, keeping columns 4 through 7.
    L means to take the lower half, keeping columns 4 through 5.
    The final R keeps the upper of the two, column 5.
    So, decoding FBFBBFFRLR reveals that it is the seat at row 44, column 5.

    Every seat also has a unique seat ID: multiply the row by 8, then add the column. In this example, the seat has ID 44 * 8 + 5 = 357.
     */
    fun findSeatId(seatCode: String): Int {
        val row = seatRow(seatCode.substring(0, 7))
        val col = seatCol(seatCode.substring(7,seatCode.length))
        return generateSeatID(row, col)
    }

    /**
     * It's a completely full flight, so your seat should be the only missing boarding pass in your list. However, there's a catch: some of the seats at the very front and back of the plane don't exist on this aircraft, so they'll be missing from your list as well.

    Your seat wasn't at the very front or back, though; the seats with IDs +1 and -1 from yours will be in your list.

    What is the ID of your seat?
     */
    fun findMissingSeatId(filledSeats: List<Int>): Int {
        var curr = filledSeats.first()
        for (i in 1 until filledSeats.size) {
            val seat = filledSeats[i]
            if (seat != ++curr) {
                return curr
            }
        }
        return 0
    }

    private fun seatRow(rowCode: String): Int {
        var low = 0
        var high = 127
        for (i in 0..5) {
            val mid = (high - low) / 2 + 1
            val rowSymbol = rowCode[i]
            if (rowSymbol == 'F') {
                high -= mid
            } else if (rowSymbol == 'B') {
                low += mid
            }
        }
        return if (rowCode[6] == 'F') low else high
    }

    private fun seatCol(colCode: String): Int {
        var low = 0
        var high = 7
        for (i in 0..1) {
            val mid = (high - low) / 2 + 1
            val colSymbol = colCode[i]
            if (colSymbol == 'L') {
                high -= mid
            } else if (colSymbol == 'R') {
                low += mid
            }
        }
        return if (colCode.last() == 'L') low else high
    }

    /**
     * return seat ID: multiply the row by 8, then add the column.
     */
    private fun generateSeatID(row: Int, col: Int): Int {
        return row * 8 + col
    }
}

fun main() {
    val seatData = FileHelpers.readFileLineByLineUsingForEachLine("./src/day5/SeatData.txt")
    val seatFinder = SeatFinder()

    var highestSeatId = Int.MIN_VALUE
    val seatIds = mutableListOf<Int>()
    for (seatCode in seatData) {
        val seatId = seatFinder.findSeatId(seatCode)
        seatIds.add(seatId)
        highestSeatId = max(highestSeatId, seatId)
    }

    // Highest Seat Id
    println("Highest seat id: $highestSeatId")

    // Find your seat
    seatIds.sort()
    val missing = seatFinder.findMissingSeatId(seatIds)
    println("The seat not filled is $missing and must be yours!")
}