/*
 *  Copyright 2023 The original authors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package dev.ilop.challenger.onebrc;

import static java.util.stream.Collectors.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collector;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

public class MainCalculateAvg {

    private static final String FILE = "./measurements.txt";

    private static record Measurement(String station, double value) {
        private Measurement(String[] parts) {
            this(parts[0], Double.parseDouble(parts[1]));
        }
    }

    private static record ResultRow(double min, double mean, double max) {

        public String toString() {
            return round(min) + "/" + round(mean) + "/" + round(max);
        }

        private double round(double value) {
            return Math.round(value * 10.0) / 10.0;
        }
    };


    public static void main(String[] args) throws IOException {
       final  Map<String, SummaryStatistics> summaryByStation = new TreeMap<>();

        Files.lines(Paths.get(FILE))
            .map(l -> new Measurement(l.split(";")))
            .forEach(m -> summaryByStation.computeIfAbsent(m.station,s-> new SummaryStatistics()).addValue(m.value));


        summaryByStation.forEach((k,s) -> System.out.print(k + "=" + s.getMin() + "/" + s.getMean() + "/" + s.getMax()));
    }
}