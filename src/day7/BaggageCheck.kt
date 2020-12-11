package day7

object BaggageCheck {
    fun parseBagDesc(descriptions: List<String>): HashMap<String, Set<String>> {
        val bagContains = hashMapOf<String, Set<String>>()
        for (desc in descriptions) {
            val bagKey = desc.substringBefore(" bags contain")
            val bagContainList = desc.substringAfter("contain ")
            val containBagTypes = bagContainList.split(",")
            val containList = hashSetOf<String>()
            for (containBagType in containBagTypes) {
                val containDesc = containBagType.trim().split(" ")
                if (containDesc[0] == "no") {
                    containList.add("no other")
                } else {
                    containList.add("${containDesc[1]} ${containDesc[2]}")
                }
            }
            bagContains[bagKey] = containList
        }
        return bagContains
    }

    fun parseBagInnerCount(descriptions: List<String>): HashMap<String, Set<InnerBag>> {
        val bagContains = hashMapOf<String, Set<InnerBag>>()
        for (desc in descriptions) {
            val bagKey = desc.substringBefore(" bags contain")
            val bagContainList = desc.substringAfter("contain ")
            val containBagTypes = bagContainList.split(",")
            val containList = hashSetOf<InnerBag>()
            for (containBagType in containBagTypes) {
                val containDesc = containBagType.trim().split(" ")
                if (containDesc[0] == "no") {
//                    containList.add("no other")
                    continue
                } else {
                    containList.add(InnerBag("${containDesc[0]}".toInt(),"${containDesc[1]} ${containDesc[2]}"))
                }
            }
            bagContains[bagKey] = containList
        }
        return bagContains
    }

    data class InnerBag(val count: Int, val bagKey: String)

    private val bagMem = HashMap<String, Int>()
    private val innerBagMem = HashMap<String, Int>()

    fun innerGold(bagKey: String, innerBags: HashMap<String, Set<InnerBag>>): Int {
        if (innerBagMem.containsKey(bagKey)) return innerBagMem[bagKey]!!
        if (bagKey == "no other") return 0
        var innerGoldCount = 0
//        val innerGoldRule = innerBags[bagKey]
        if (innerBagMem.containsKey(bagKey)) {
            return innerBagMem[bagKey]!!
        } else {
            val innerBagRule = innerBags[bagKey]!!
            for (innerBag in innerBagRule) {
                innerGoldCount += innerBag.count
                innerGoldCount += innerBag.count * innerGold(innerBag.bagKey, innerBags)
            }
            innerBagMem[bagKey] = innerGoldCount
        }
        return innerGoldCount
    }
    fun outerBagCount(bagRules: HashMap<String, Set<String>>): Int {
        var validOuterBags = 0
        for (bagRule in bagRules) {
            if (!bagMem.containsKey(bagRule.key)) {
                bagMem[bagRule.key] = canHoldGold(bagRule.key, bagRules)
            }
        }

        for (gold in bagMem) {
            if (gold.value > 0) {
                validOuterBags++
            }
        }
        return validOuterBags
    }

    private fun canHoldGold(bag: String, bagRules: HashMap<String, Set<String>>): Int {
        if (bagMem.containsKey(bag)) return bagMem[bag]!!

        var goldCount = 0
        val bagRule = bagRules[bag]!!
        if (bagRule.contains("shiny gold")) {
            goldCount++
        }
        for (innerBag in bagRule) {
            if (innerBag == "no other") break

            if (bagMem.containsKey(innerBag)) {
                if (bagMem[innerBag]!! > 0) {
                    goldCount++
                }
            } else {
                bagMem[innerBag] = canHoldGold(innerBag, bagRules)
                if (bagMem[innerBag]!! > 0) {
                    goldCount++
                }
            }
        }
        return goldCount
    }
}

fun main() {
    val bagTypeList = FileHelpers.readFileLineByLineUsingForEachLine("./src/day7/day7Bag.txt")
//    val bagTypeList = FileHelpers.readFileLineByLineUsingForEachLine("./src/day7/day7BagTest.txt")
//    val bagRules = BaggageCheck.parseBagDesc(bagTypeList)
//    val outerCount = BaggageCheck.outerBagCount(bagRules)
//    println("$outerCount")

    val innerBagRules = BaggageCheck.parseBagInnerCount(bagTypeList)
    val innerGold = BaggageCheck.innerGold("shiny gold", innerBagRules)
    println("$innerGold")
}