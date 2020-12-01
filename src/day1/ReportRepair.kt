package day1

import FileHelpers.Companion.toIntList

/**
 * Specifically, they need you to find the two entries that sum to 2020 and then multiply those two numbers together.
 * https://adventofcode.com/2020/day/1
 */
class ReportRepair {

    /**
     * Specifically, they need you to find the two entries that sum to 2020 and then multiply those two numbers together.

    For example, suppose your expense report contained the following:

    1721
    979
    366
    299
    675
    1456
    In this list, the two entries that sum to 2020 are 1721 and 299. Multiplying them together produces 1721 * 299 = 514579, so the correct answer is 514579.
     */
    fun reportRepairTwoSum(expenses: List<Int>, target: Int = 2020): Int {
        val remainders = HashSet<Int>()
        for (expense: Int in expenses) {
            val currRemain = target - expense
            if (remainders.contains(currRemain)) {
                val prod = expense * currRemain
                println("Two number report $expense and $currRemain add up to $target and produces ($expense x $currRemain = $prod)")
                return prod
            } else {
                remainders.add(expense)
            }
        }
        return -1
    }

    /**
     * Using the above example again, the three entries that sum to 2020 are 979, 366, and 675.
     * Multiplying them together produces the answer, 241861950.
     * In your expense report, what is the product of the three entries that sum to 2020?
     */
    fun reportRepairThreeSum(expenses: List<Int>): Int {
        // approach - reduce to two sum and solve
        for (expense: Int in expenses) {
            val remainTarget = 2020-expense
            val twoSum = reportRepairTwoSum(expenses, remainTarget)
            if (twoSum > 0) {
                val prod = expense * twoSum
                println("Three number report $expense and two sum report add up to 2020 and produces ($expense x $twoSum = $prod)")
                return prod
            }
        }
        return -1
    }
}


fun main() {
    val reportRepair = ReportRepair()
    val reportRepairInputs = FileHelpers.readFileLineByLineUsingForEachLine("./src/day1/day1input.txt").toIntList()

    val twoSumReport = reportRepair.reportRepairTwoSum(reportRepairInputs)
    println("Two sum report = $twoSumReport")

    val threeSumReport = reportRepair.reportRepairThreeSum(reportRepairInputs)
    println("Three sum report = $threeSumReport")
}