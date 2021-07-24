/*
 * Copyright 2021 SOUP
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.anggit97.core.util

import android.annotation.SuppressLint
import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.WeekFields
import java.util.*

private val ZONE_JAKARTA = ZoneId.of("Asia/Jakarta")
private const val LOCALE_ID = "id"
fun currentTime(): LocalDateTime = LocalDateTime.now(ZONE_JAKARTA)
fun today(): LocalDate = LocalDate.now(ZONE_JAKARTA)
fun yesterday(): LocalDate = today().minusDays(1)

/**
 * 매달 마지막 수요일은 "문화가 있는 날"
 * @link http://www.culture.go.kr/wday
 *
 * '문화의 날' 바로 직전의 '일/월/화/수'요일은 '문화의 날' 주간으로 정한다.
 */
fun LocalDate.isInWeekOfCultureDay(): Boolean {
    val lastDayOfMonth = LocalDate.of(year, month, month.maxLength())
    val cultureDayOfMonth = lastDayOfMonth.minusDaysTo(DayOfWeek.WEDNESDAY)
    val mondayOfCultureDay = cultureDayOfMonth.minusDaysTo(DayOfWeek.SUNDAY)
    return this in mondayOfCultureDay..cultureDayOfMonth
}

fun LocalDate.minusDaysTo(dayOfWeekToSubtract: DayOfWeek): LocalDate {
    return minusDays(dayOfWeek.calculateMinusDaysTo(dayOfWeekToSubtract))
}

fun LocalDateTime.minusDaysTo(dayOfWeekToSubtract: DayOfWeek): LocalDateTime {
    return minusDays(dayOfWeek.calculateMinusDaysTo(dayOfWeekToSubtract))
}

fun LocalDate.plusDaysTo(nextDayOfWeek: DayOfWeek): LocalDate {
    return plusDays(dayOfWeek.calculatePlusDaysTo(nextDayOfWeek))
}

fun LocalDateTime.plusDaysTo(nextDayOfWeek: DayOfWeek): LocalDateTime {
    return plusDays(dayOfWeek.calculatePlusDaysTo(nextDayOfWeek))
}

fun DayOfWeek.calculateMinusDaysTo(dayOfWeekToPrevious: DayOfWeek): Long {
    var result = value - dayOfWeekToPrevious.value
    if (result < 0) {
        result += DayOfWeek.SUNDAY.value
    }
    return result.toLong()
}

fun DayOfWeek.calculatePlusDaysTo(dayOfWeekToNext: DayOfWeek): Long {
    var result = dayOfWeekToNext.value - value
    if (result < 0) {
        result += DayOfWeek.SUNDAY.value
    }
    return result.toLong()
}

fun LocalDate.MM_DD(): String {
    return formatter("MM.dd")
}

fun LocalDate.YYYY_MM_DD(): String {
    return formatter("yyyy.MM.dd")
}

fun LocalDate.DD_MM_YYYY(): String {
    return formatter("dd MMMM yyyy")
}

fun LocalDate.formatter(pattern: String): String {
    return format(DateTimeFormatter.ofPattern(pattern, Locale(LOCALE_ID)))
}

fun LocalDate.weekOfYear(): Int {
    return get(WeekFields.of(java.util.Locale.KOREA).weekOfWeekBasedYear())
}

@SuppressLint("SimpleDateFormat")
fun String.toLocalDate(): LocalDate {
    return LocalDate.parse(this)
}
