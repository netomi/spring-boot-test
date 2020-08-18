/*
 *  Copyright (c) 2020 Thomas Neidhart.
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
package com.github.netomi.spring.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;
import java.util.Locale;

public class Extensions {

    private static final DateTimeFormatter ENGLISH_DATE_FORMATTER =
            new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd")
                    .toFormatter(Locale.ENGLISH);

    private Extensions() {}

    public static String toSlug(String text) {
        String[] arr =
            text.toLowerCase()
                .replace("\n", " ")
                .replaceAll("[^a-z\\d\\s]", " ")
                .split(" ");

        return String.join("-", Arrays.asList(arr)).replaceAll("-+", "-");
    }

    public static String format(LocalDateTime time) {
        return ENGLISH_DATE_FORMATTER.format(time);
    }
}
