import java.io.File

class FileHelpers {
    companion object {
        fun readFileLineByLineUsingForEachLine(fileName: String): List<String> {
            val inputs = mutableListOf<String>()
            File(fileName).forEachLine {
                inputs.add(it)
            }
            return inputs
        }

        fun List<String>.toIntList(): List<Int> = this.map { it.toInt() }
    }
}