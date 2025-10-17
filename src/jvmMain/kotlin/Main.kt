import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import kotlin.time.measureTimedValue

const val FILE_PATH: String = "src/commonMain/resources/measurements-1000000000.txt"

fun main() {
  measureTimedValue {
    val results: MutableMap<String, Result> = hashMapOf()
    FileInputStream(FILE_PATH).use { stream ->
      InputStreamReader(stream, Charsets.UTF_8.name()).use { reader ->
        BufferedReader(reader).use { buffer ->
          var line: String?
          while (true) {
            line = buffer.readLine() ?: break
            val (city, temp) = parseLine(line)
            if (results.containsKey(city)) {
              results[city] = Result(
                min = minOf(results[city]!!.min, temp),
                max = maxOf(results[city]!!.max, temp),
                sum = results[city]!!.sum + temp,
                count = results[city]!!.count + 1,
              )
            } else {
              results[city] = Result(
                min = temp,
                max = temp,
                sum = temp,
                count = 1,
              )
            }
          }
        }
      }
    }

    results
      .entries
      .sortedBy { it.key }
      .joinToString(
        prefix = "{",
        postfix = "}",
        separator = ", "
      ) {
        val avg = String.format("%.1f", (it.value.sum.toDouble() / it.value.count) / 10.0)
        "${it.key}=${String.format("%.1f", it.value.min / 10.0)}/$avg/${String.format("%.1f", it.value.max / 10.0)}"
      }
  }.also {
    println(it.value)
    println("It took ${it.duration.inWholeSeconds} seconds")
  }
}

fun parseLine(line: String): Pair<String, Int> {
  val sep = line.indexOf(';')
  val city = line.take(sep)

  var i = sep + 1
  var sign = 1

  if (line[i] == '-') {
    sign = -1
    i++
  }

  var value = 0

  // parte inteira (1 ou 2 dígitos)
  while (line[i] != '.') {
    value = value * 10 + (line[i] - '0')
    i++
  }

  i++ // pula '.'

  // parte decimal (1 dígito)
  val decimal = line[i] - '0'

  return city to ((value * 10 + decimal) * sign)
}

data class Result(
  val min: Int = Int.MAX_VALUE,
  val max: Int = Int.MIN_VALUE,
  val sum: Int = 0,
  val count: Int = 0,
)
