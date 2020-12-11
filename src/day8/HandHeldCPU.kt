package day8

import java.util.*
import kotlin.collections.HashSet

class HandHeldCPU {
    var acc = 0
    var currInstIndex = 0
    val completedInstructions = HashSet<Int>()

    fun exeDebug(instructions: List<String>): Int {
        while (currInstIndex < instructions.size) {
            if (completedInstructions.contains(currInstIndex)) {
                return acc
            }
            val currInst = instructions[currInstIndex].split(" ")
            completedInstructions.add(currInstIndex)
            when (currInst[0]) {
                "nop" -> nop()
                "acc" -> {
                    val accNum = currInst[1].subSequence(1, currInst[1].length).toString()
                    acc(accNum.toInt(), currInst[1].first() == '+')
                }
                "jmp" -> {
                    val jmpNum = currInst[1].subSequence(1, currInst[1].length).toString()
                    jmp(jmpNum.toInt(), currInst[1].first() == '+')
                }
            }

        }
        return 0
    }

    fun accFixed(instructions: MutableList<String>): Int {
        val instructionQueue = LinkedList<List<String>>()
        instructionQueue.add(instructions)
        for (i in 0 until instructions.size) {
            val inst = instructions[i]
            if (inst.contains("jmp")) {
                val newInst = inst.replace("jmp", "nop")
                instructions[i] = newInst
                instructionQueue.add(instructions.toMutableList())
                instructions[i] = inst
            }
        }

        while (instructionQueue.isNotEmpty()) {
            val newInst = instructionQueue.poll()
            val success = HandHeldCPU().exeWillComplete(newInst)
            if (success != null) {
                return success.acc
            }
        }
        return 0
    }

    fun exeWillComplete(instructions: List<String>): HandHeldCPU? {
        while (currInstIndex < instructions.size) {
            if (completedInstructions.contains(currInstIndex)) {
                return null
            }
            val currInst = instructions[currInstIndex].split(" ")
            completedInstructions.add(currInstIndex)
            when (currInst[0]) {
                "nop" -> nop()
                "acc" -> {
                    val accNum = currInst[1].subSequence(1, currInst[1].length).toString()
                    acc(accNum.toInt(), currInst[1].first() == '+')
                }
                "jmp" -> {
                    val jmpNum = currInst[1].subSequence(1, currInst[1].length).toString()
                    jmp(jmpNum.toInt(), currInst[1].first() == '+')
                }
            }

        }
        return this
    }

    fun nop() {
        currInstIndex++
    }

    fun acc(accValue: Int, isPositive: Boolean) {
        if (isPositive) {
            acc += accValue
        } else {
            acc -= accValue
        }
        nop()
    }

    fun jmp(pos: Int, isPositive: Boolean) {
        if (isPositive) {
            currInstIndex += pos
        } else {
            currInstIndex -= pos
        }
    }
}

fun  main() {
//    val cpuInstructions = FileHelpers.readFileLineByLineUsingForEachLine("./src/day8/day8TestInput.txt")
    val cpuInstructions = FileHelpers.readFileLineByLineUsingForEachLine("./src/day8/day8CpuInst.txt")
//    val cpuDebug = HandHeldCPU(cpuInstructions).exeDebug()
//    println("Acc before loop: $cpuDebug")

    val cpuFix = HandHeldCPU().accFixed(cpuInstructions.toMutableList())
    println("Acc after fix: $cpuFix")
}