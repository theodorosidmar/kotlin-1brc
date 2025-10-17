#!/bin/bash

JAVA_OPTS="-cp /home/theodorosidmar/workspace/kotlin-1brc/build/classes/kotlin/jvm/main:/home/theodorosidmar/workspace/kotlin-1brc/build/classes/java/jvmMain:/home/theodorosidmar/workspace/kotlin-1brc/build/processedResources/jvm/main:/home/theodorosidmar/.gradle/caches/modules-2/files-2.1/org.jetbrains.kotlin/kotlin-stdlib/2.3.0-RC/70349b7f9b9b3c01fcd221c97f30350e3f245f4d/kotlin-stdlib-2.3.0-RC.jar:/home/theodorosidmar/.gradle/caches/modules-2/files-2.1/org.jetbrains/annotations/13.0/919f0dfe192fb4e063e7dacadee7f8bb9a2672a9/annotations-13.0.jar"
JAVA_OPTS="$JAVA_OPTS --enable-native-access=ALL-UNNAMED --add-modules=jdk.incubator.vector"
JAVA_OPTS="$JAVA_OPTS -Xlog:aot -XX:AOTCacheOutput=build/libs/onebrc.aot"
JAVA_OPTS="$JAVA_OPTS -XX:+UnlockExperimentalVMOptions -XX:+UnlockDiagnosticVMOptions"
JAVA_OPTS="$JAVA_OPTS -XX:-TieredCompilation -XX:MaxInlineSize=10000 -XX:InlineSmallCode=10000 -XX:FreqInlineSize=10000"
JAVA_OPTS="$JAVA_OPTS -XX:-UseCountedLoopSafepoints -XX:GuaranteedSafepointInterval=0"
JAVA_OPTS="$JAVA_OPTS -XX:+TrustFinalNonStaticFields -da -dsa -XX:+UseNUMA -XX:-EnableJVMCI"
JAVA_OPTS="$JAVA_OPTS -Djdk.incubator.vector.VECTOR_ACCESS_OOB_CHECK=0"
JAVA_OPTS="${JAVA_OPTS} -Dfile.path=src/commonMain/resources/measurements-10000-unique-keys.txt"
if [[ ! "$(uname -s)" = "Darwin" ]]; then
  JAVA_OPTS="$JAVA_OPTS -XX:+UseTransparentHugePages"
fi

#java -Djarmode=tools -jar build/libs/onebrc-jvm.jar extract --destination build/libs

java $JAVA_OPTS MainKt
