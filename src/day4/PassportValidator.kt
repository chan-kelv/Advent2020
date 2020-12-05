package day4

class PassportValidator {
    /**
     * The automatic passport scanners are slow because they're having trouble detecting which passports have all required fields. The expected fields are as follows:

    byr (Birth Year)
    iyr (Issue Year)
    eyr (Expiration Year)
    hgt (Height)
    hcl (Hair Color)
    ecl (Eye Color)
    pid (Passport ID)
    cid (Country ID)
    Passport data is validated in batch files (your puzzle input). Each passport is represented as a sequence of key:value pairs separated by spaces or newlines. Passports are separated by blank lines.
     */
    fun passportValidateCount(passportBatches: List<String>): Int {
        val passportCollection = mutableListOf<List<String>>()
        var passportData = mutableListOf<String>()
        for (passportDataRaw in passportBatches) {
            if (passportDataRaw == "") {
                passportCollection.add(passportData)
                passportData = mutableListOf()
            } else {
                passportData.add(passportDataRaw.trim())
            }
        }
        passportCollection.add(passportData) // last one

        var validPassportCount = 0
        for (passport in passportCollection) {
            if (Passport.generatePassport(passport) != null) {
                validPassportCount++
            }
        }

        return validPassportCount
    }

    class Passport {
        var byr: String? = null // Birth year
        var iyr: String? = null // issue year
        var eyr: String? = null // expire year
        var hgt: String? = null // height cm/in
        var hcl: String? = null // hair colour
        var ecl: String? = null // eye colour
        var pid: String? = null // passport id
        var cid: String? = null // country id - optional

        private fun isValid(): Boolean {
            return byrValid() &&
                    iyrValid() &&
                    eyrValid() &&
                    hgtValid() &&
                    hclValid() &&
                    eclValid() &&
                    pidValid()
        }

        /**
         * four digits; at least 1920 and at most 2002
         */
        private fun byrValid(): Boolean {
            byr?.toIntOrNull()?.let {
                return it in 1920..2002
            }
            return false

        }

        /**
         * four digits; at least 2010 and at most 2020
         */
        private fun iyrValid(): Boolean {
            iyr?.toIntOrNull()?.let {
                return it in 2010..2020
            }
            return false
        }

        /**
         * four digits; at least 2020 and at most 2030
         */
        private fun eyrValid(): Boolean {
            eyr?.toIntOrNull()?.let {
                return it in 2020..2030
            }
            return false
        }

        /**
         * a number followed by either cm or in:
           If cm, the number must be at least 150 and at most 193.
           If in, the number must be at least 59 and at most 76.
         */
        private fun hgtValid(): Boolean {
            hgt?.let {
                if (it.contains("cm")) {
                    val heightNum = it.split("cm").first().toInt()
                    return heightNum in 150..193
                } else if (it.contains("in")) {
                    val heightNum = it.split("in").first().toInt()
                    return heightNum in 59..76
                }
            }
            return false
        }

        /**
         * a # followed by exactly six characters 0-9 or a-f
         */
        private fun hclValid(): Boolean {
            hcl?.let {
                val hclValidRegex = "#([a-f]|[0-9]){6}".toRegex()
                return it.startsWith("#") &&
                        hclValidRegex.matches(it) &&
                        it.length == 7
            }
            return false
        }

        /**
         * exactly one of: amb blu brn gry grn hzl oth
         */
        private fun eclValid(): Boolean {
            ecl?.let {
                return it == "amb" || it == "blu" || it == "brn" || it == "gry" || it == "grn" || it == "hzl" || it == "oth"
            }
            return false
        }

        /**
         * a nine-digit number, including leading zeroes
         */
        private fun pidValid(): Boolean {
            pid?.toIntOrNull()?.let {
                return pid?.length == 9
            }
            return false
        }



        companion object {
            /**
             * returns: a passport object if valid else null
             */
            fun generatePassport(passportFields: List<String>): Passport? {
                val passport = Passport()
                for (fieldLine: String in passportFields) {
                    for (field: String in fieldLine.split(" ")) {
                        val fieldArr = field.split(":")
                        val fieldKey = fieldArr[0]
                        val fieldVal = fieldArr[1]
                        when (fieldKey) {
                            "byr" -> passport.byr = fieldVal
                            "iyr" -> passport.iyr = fieldVal
                            "eyr" -> passport.eyr = fieldVal
                            "hgt" -> passport.hgt = fieldVal
                            "hcl" -> passport.hcl = fieldVal
                            "ecl" -> passport.ecl = fieldVal
                            "pid" -> passport.pid = fieldVal
                            "cid" -> passport.cid = fieldVal
                        }
                    }
                }

                return if (passport.isValid()) passport else null
            }
        }
    }
}

fun main() {
    val passportBadData = FileHelpers.readFileLineByLineUsingForEachLine("./src/day4/passportBadData.txt")
    val badCount = PassportValidator().passportValidateCount(passportBadData)
    println("There are $badCount number of invalid passports in badDataBatch") // 0

    val passportGoodData = FileHelpers.readFileLineByLineUsingForEachLine("./src/day4/passportGoodData.txt")
    val goodCount = PassportValidator().passportValidateCount(passportGoodData)
    println("There are $goodCount number of valid passports in goodDataBatch") // 4

    val passportTestData = FileHelpers.readFileLineByLineUsingForEachLine("./src/day4/day4PassportTestData.txt")
    val testCount = PassportValidator().passportValidateCount(passportTestData)
    println("There are $testCount number of valid passports in testBatch") // 2

    val passportData = FileHelpers.readFileLineByLineUsingForEachLine("./src/day4/day4PassportBatch.txt")
    val validPassportCount = PassportValidator().passportValidateCount(passportData)
    println("There are $validPassportCount number of valid passports in the batch")
}