package day6

object CustomsDeclare {
    fun numOfCheckedYes(declaredFields: List<String>): Int {
        val checkedFields = HashSet<Char>()
        for (fields in declaredFields) {
            for (field in fields.toCharArray()) {
                checkedFields.add(field)
            }
        }
        return checkedFields.size
    }

    fun numOfSimilar(declaredFields: List<String>): Int {
//        val checkedFields = HashSet<Char>()
        val declaredCount = IntArray(26)
        for (fields in declaredFields) {
            for (field in fields.toCharArray()) {
//                checkedFields.add(field)
                val fieldIndex = field - 'a'
                declaredCount[fieldIndex]++
            }
        }
        var checkedFields = 0
        val groupCount = declaredFields.size
        for (i in 0..25) {
            if (declaredCount[i] == groupCount) {
                checkedFields++
            }
        }
        return checkedFields
    }
}

fun main() {
//    val customsGroups = FileHelpers.readFileLineByLineUsingForEachLine("./src/day6/day6CustomsFormExample.txt")
    val customsGroups = FileHelpers.readFileLineByLineUsingForEachLine("./src/day6/day6CustomsForms.txt")
    val customsCollective = mutableListOf<String>()
    var customSum = 0
    var customSimilarSum = 0
    for (customsGroup in customsGroups) {
        if (customsGroup.isBlank()) {
            val numDeclared = CustomsDeclare.numOfCheckedYes(customsCollective)
            println("This group declared: $numDeclared items")
            customSum += numDeclared

            val numSimilar = CustomsDeclare.numOfSimilar(customsCollective)
            customSimilarSum += numSimilar
            println("This group similar: $numSimilar items")
            customsCollective.clear()
        } else {
            customsCollective.add(customsGroup)
        }
    }
    val numDeclared = CustomsDeclare.numOfCheckedYes(customsCollective) // last one
    println("This group declared: $numDeclared items")
    customSum += numDeclared

    val numSimilar = CustomsDeclare.numOfSimilar(customsCollective)
    customSimilarSum += numSimilar
    println("This group similar: $numSimilar items")

    println("Total sum: $customSum")
    println("Total similar sum: $customSimilarSum")
}