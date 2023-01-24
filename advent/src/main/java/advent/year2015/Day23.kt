package advent.year2015

object Day23 {
    fun calculateRegisters(input: String, start: Map<String, Int> = emptyMap()): Map<String, Int> {
        val lines = input.lineSequence().filter { it.isNotBlank() }.toList()
        var index = 0
        val registers = HashMap(start)
        while (index < lines.size && index >= 0) {
            val line = lines[index]
            if (line.startsWith("hlf")) {
                val register = line.substring(line.indexOf(' ') + 1)
                val registerValue = registers.getOrElse(register) { 0 }
                registers[register] = registerValue / 2
                index++
            } else if (line.startsWith("tpl")) {
                val register = line.substring(line.indexOf(' ') + 1)
                val registerValue = registers.getOrElse(register) { 0 }
                registers[register] = registerValue * 3
                index++
            } else if (line.startsWith("inc")) {
                val register = line.substring(line.indexOf(' ') + 1)
                val registerValue = registers.getOrElse(register) { 0 }
                registers[register] = registerValue + 1
                index++
            } else if (line.startsWith("jmp")) {
                val jumpAmount = line.substring(line.indexOf(' ') + 1).toInt()
                index += jumpAmount
            } else if (line.startsWith("jie")) {
                val separatorIndex = line.indexOf(',')
                val register = line.substring(line.indexOf(' ') + 1, separatorIndex)
                val jumpAmount = line.substring(separatorIndex + 2).toInt()
                if (registers.getOrElse(register) { 0 } % 2 == 0) {
                    index += jumpAmount
                } else {
                    index++
                }
            } else if (line.startsWith("jio")) {
                val separatorIndex = line.indexOf(',')
                val register = line.substring(line.indexOf(' ') + 1, separatorIndex)
                val jumpAmount = line.substring(separatorIndex + 2).toInt()
                if (registers.getOrElse(register) { 0 } == 1) {
                    index += jumpAmount
                } else {
                    index++
                }
            }
        }
        return registers
    }
}
