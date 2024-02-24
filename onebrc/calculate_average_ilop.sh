#!/bin/sh

time {
    JAVA_OPTS=""
    java $JAVA_OPTS --class-path target/onebrc-1.0-SNAPSHOT-jar-with-dependencies.jar dev.ilop.challenger.onebrc.MainCalculateAvg
}
