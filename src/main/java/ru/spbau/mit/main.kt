package ru.spbau.mit

import java.util.*

fun notInRange(range: Int, letter: Char): Boolean {
    return ((letter < 'a') or (letter >= 'a' + range)) and (letter != '?')
}

fun fillPattern(pattern: CharArray, range: Int): CharArray? {
    return CharArray((pattern.size + 1) / 2, {
        i ->
        val a = pattern[i]
        val b = pattern[pattern.size - i - 1]
        when {
            notInRange(range, a) or notInRange(range, b) -> return null
            a == b -> a
            a == '?' -> b
            b == '?' -> a
            else -> return null
        }
    })
}

fun main(args: Array<String>) {
    with(Scanner(System.`in`)) {
        val k = nextInt()
        nextLine()
        val patternString = nextLine()
        val odd = patternString.length % 2
        val pattern = fillPattern(patternString.toCharArray(), k)
        if (pattern == null) {
            print("IMPOSSIBLE")
            return
        }
        val unusedLetters = ('a' until ('a' + k)).filter { ! pattern.contains(it) }
        val questionCount = pattern.count { it == '?' }
        if (unusedLetters.size > questionCount) {
            print("IMPOSSIBLE")
            return
        }
        var j = 0
        for (i in 0 until pattern.size) {
            if (pattern[i] == '?') {
                if (questionCount - j > unusedLetters.size)
                    pattern[i] = 'a'
                else
                    pattern[i] = unusedLetters[j + unusedLetters.size - questionCount]
                j++
            }
        }
        pattern.forEach { print(it) }
        pattern.reverse()
        (odd until pattern.size).forEach { print(pattern[it]) }
    }
}
