import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString
import platform.posix.fclose
import platform.posix.fgets
import platform.posix.fopen
import platform.posix.getenv

@OptIn(ExperimentalForeignApi::class)
fun main() {
  val filename = getenv("FILE_PATH")?.toKString() ?: "src/commonMain/resources/measurements-10000-unique-keys.txt"
  val file = fopen(filename, "r")
  val sb = buildString {
    memScoped {
      val readBufferLength = 64 * 1024
      val buffer = allocArray<ByteVar>(readBufferLength)
      var line = fgets(buffer, readBufferLength, file)?.toKString()
      while (line != null) {
        append(line)
        line = fgets(buffer, readBufferLength, file)?.toKString()
      }
    }
  }
  fclose(file)
  println(sb)
  println("done")
}
