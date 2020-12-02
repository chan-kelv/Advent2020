package day2

class PasswordValidator {
    /**
        For example, suppose you have the following list:

        1-3 a: abcde
        1-3 b: cdefg
        2-9 c: ccccccccc
        Each line gives the password policy and then the password. The password policy indicates the lowest and highest number of times a given letter must appear for the password to be valid. For example, 1-3 a means that the password must contain a at least 1 time and at most 3 times.

        In the above example, 2 passwords are valid. The middle password, cdefg, is not; it contains no instances of b, but needs at least 1. The first and third passwords are valid: they contain one a or nine c, both within the limits of their respective policies.

        How many passwords are valid according to their policies?
     */
    fun numValidPasswordsRangeRule(passwords: List<String>): Int {
        var validPw = 0
        for (pwAttempt: String in passwords) { // eg "1-3 a: abcde"
            val inputComponents = pwAttempt.split(" ") // eg ["1-3", "a:", "abcde"]

            val minMaxChar = getFirstAndSecondNum(inputComponents[0]) // eg "1-3" returns [min, max]
            val min = minMaxChar[0]
            val max = minMaxChar[1]

            val pwRuleChar = inputComponents[1].first() // assuming single char rule --> eg "a"

            if (passwordCountRuleValid(inputComponents[2], min, max, pwRuleChar)) {
                validPw++
            }
        }
        return validPw
    }

    /**
        Each policy actually describes two positions in the password, where 1 means the first character, 2 means the second character, and so on. (Be careful; Toboggan Corporate Policies have no concept of "index zero"!) Exactly one of these positions must contain the given letter. Other occurrences of the letter are irrelevant for the purposes of policy enforcement.

        Given the same example list from above:

        1-3 a: abcde is valid: position 1 contains a and position 3 does not.
        1-3 b: cdefg is invalid: neither position 1 nor position 3 contains b.
        2-9 c: ccccccccc is invalid: both position 2 and position 9 contain c.
        How many passwords are valid according to the new interpretation of the policies?
     */
    fun numValidPwPositionRule(passwords: List<String>): Int {
        var validPw = 0
        for (pwAttempt: String in passwords) {
            val inputComponents = pwAttempt.split(" ") // eg ["1-3", "a:", "abcde"]

            val pwIndexes = getFirstAndSecondNum(inputComponents[0]) // eg "1-3" returns [min, max]
            val firstIndex = pwIndexes[0]
            val secondIndex = pwIndexes[1]

            val pwRuleChar = inputComponents[1].first() // assuming single char rule --> eg "a"
            
            if (passwordPosRuleValid(inputComponents[2], firstIndex, secondIndex, pwRuleChar)) {
                validPw++
            }
        }
        return validPw
    }

    /**
     * (index are 1 based)
     * If first pos has the ruleChar, second cant. Else the second must be the ruleChar to be valid
     */
    private fun passwordPosRuleValid(pw: String, firstIndex: Int, secondIndex: Int, pwRuleChar: Char): Boolean {
        if (pw[firstIndex - 1] == pwRuleChar) {
            return pw[secondIndex - 1] != pwRuleChar
        }
        return pw[secondIndex - 1] == pwRuleChar
    }

    /**
     * count the number of occurrences of the rule char. If it falls within min-max inclusively, it is valid
     */
    private fun passwordCountRuleValid(pw: String, min: Int, max: Int, pwRuleChar: Char): Boolean {
        var ruleCharCount = 0;
        for (c: Char in pw) {
            if (c == pwRuleChar) {
                ruleCharCount++
            }
        }

        return ruleCharCount in min..max // inclusive
    }

    /**
     * Splits "1-3" into [1,3]
     */
    private fun getFirstAndSecondNum(minMaxRaw: String): IntArray {
        val minMax = IntArray(2)
        val minMaxArr = minMaxRaw.split("-")
        minMax[0] = minMaxArr[0].toInt()
        minMax[1] = minMaxArr[1].toInt()
        return minMax
    }
}

fun main() {
    val pwVd = PasswordValidator()
    val pwInputs = FileHelpers.readFileLineByLineUsingForEachLine("./src/day2/pwRules.txt")

    val validPwCount = pwVd.numValidPasswordsRangeRule(pwInputs)
    println("There are $validPwCount passwords that follow the range rule")

    val validPwPosCount = pwVd.numValidPwPositionRule(pwInputs)
    println("There are $validPwPosCount passwords that follow the position rule")
}