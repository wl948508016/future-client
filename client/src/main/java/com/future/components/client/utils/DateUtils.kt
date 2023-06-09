package com.future.components.client.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*


/**
 *
 * @Description:    时间工具
 * @Author:         future
 * @CreateDate:     6/28/21 3:24 PM
 */

/**
 * 获取当前系统时间戳，单位毫秒
 */
val curTime: Long
    get() = System.currentTimeMillis()

/**
 * 获取当前年份
 */
val curYear: Int
    get() = curTime.getDateYear()

/**
 * 获取当前月份
 */
val curMonth: Int
    get() = curTime.getDateMonth()

/**
 * 获取当前日
 */
val curDay: Int
    get() = curTime.getDateDay()

/**
 * 获取当前小时
 */
val curHour: Int
    get() = curTime.getDateHour()

/**
 * 获取当前分钟
 */
val curMinute: Int
    get() = curTime.getDateMinute()

/**
 * 获取当前秒钟
 */
val curSecond: Int
    get() = curTime.getDateSecond()

const val YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss"
const val YYYY_MM_DD = "yyyy-MM-dd"
const val YYYY_MM = "yyyy-MM"
const val YYYY_MM2 = "yyyy年MM月"
const val MM_DD = "MM-dd"
const val HH_MM = "HH:mm"

/**
 * 获取时间戳中的年份
 * @return [Int] 年份
 */
fun Long.getDateYear(): Int {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return calendar.get(Calendar.YEAR)
}

/**
 * 获取时间戳中的月份
 * @return [Int] 月份
 */
fun Long.getDateMonth(): Int {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return calendar.get(Calendar.MONTH) + 1
}

/**
 * 获取时间戳中的日
 * @return [Int] 日
 */
fun Long.getDateDay(): Int {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return calendar.get(Calendar.DAY_OF_MONTH)
}

/**
 * 获取时间戳中的小时 24小时
 * @return [Int] 小时
 */
fun Long.getDateHour(): Int {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return calendar.get(Calendar.HOUR_OF_DAY)
}

/**
 * 获取时间戳中的分钟
 * @return [Int] 分钟
 */
fun Long.getDateMinute(): Int {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return calendar.get(Calendar.MINUTE)
}

/**
 * 获取时间戳中的秒钟
 * @return [Int] 秒钟
 */
fun Long.getDateSecond(): Int {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = this
    return calendar.get(Calendar.SECOND)
}

/**
 * @param oldPattern 老时间样式 yyyy-MM-dd
 * @param newPattern 新时间样式 MM-dd
 * 将旧的时间字符串格式转换成想要的格式
 * @return [String] 时间字符串
 */
fun String.formatDateStr(oldPattern: String = YYYY_MM_DD,newPattern:String = MM_DD):String{
    return toDateLong(oldPattern).toDateStr(newPattern)
}

/**
 * @param oldPattern 老时间样式 yyyy-MM-dd
 * @param newPattern 新时间样式 MM-dd
 * 将旧的时间字符串格式转换成想要的格式
 * @return [String] 时间字符串
 */
fun String.chartFormatDateStr(oldPattern: String = YYYY_MM_DD_HH_MM_SS,newPattern:String = HH_MM):String{
    return toDateLong(oldPattern).toDateStr(newPattern)
}

/**
 * 时间戳转换成字符窜
 * @param pattern 时间样式 yyyy-MM-dd HH:mm:ss
 * @return [String] 时间字符串
 */
@SuppressLint("SimpleDateFormat")
fun Long.toDateStr(pattern: String = YYYY_MM_DD): String {
    val date = Date(this)
    val format = SimpleDateFormat(pattern)
    return format.format(date)
}

/**
 * 将字符串转为时间戳
 * @param pattern 时间样式 yyyy-MM-dd HH:mm:ss
 * @return [String] 时间字符串
 */
fun String.toDateLong(pattern: String = YYYY_MM_DD_HH_MM_SS): Long {
    @SuppressLint("SimpleDateFormat")
    val dateFormat = SimpleDateFormat(pattern)
    var date: Date? = Date()
    try {
        date = dateFormat.parse(this)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return date?.time ?: 0
}

/**
 * 北斗天地后台接口要求，获取当天日期范围
 */
fun Long.bdCurDayString(pattern: String = YYYY_MM_DD): Array<String> {
    val date = Date(this)
    val formatString: String = SimpleDateFormat(pattern).format(date)
    return arrayOf(
        StringBuilder(formatString).append(" 00:00:00").toString(),
        StringBuilder(formatString).append(" 23:59:59").toString()
    )
}

/**
 * 北斗天地后台接口要求，获取当天日期范围
 */
fun String.bdCurDayString(): Array<String> {
    return arrayOf(
        StringBuilder(this).append(" 00:00:00").toString(),
        StringBuilder(this).append(" 23:59:59").toString()
    )
}

/**
 * 北斗天地后台接口要求，获取当天日期范围
 */
fun String.bdCurMonthString():Array<String>{
    return arrayOf(
        StringBuilder(this).append("-01").toString(),
        StringBuilder(this).append("-30").toString()
    )
}

/**
 * 根据年月日获取时间戳
 * @param year 年
 * @param month 月
 * @param day 日
 * @return [Long] 时间戳
 */
fun getDateFromYMD(year: Int = curYear, month: Int = curMonth, day: Int = curDay): Long {
    return getDateFromYMDHMS(year, month, day, 0, 0, 0)
}

/**
 * 根据年月日时分秒获取时间戳
 * @param year Int 年
 * @param month Int 月
 * @param day Int 日
 * @param hour Int 时
 * @param minute Int 分
 * @param second Int 秒
 * @return [Long] 时间戳
 */
fun getDateFromYMDHMS(
    year: Int = curYear,
    month: Int = curMonth,
    day: Int = curDay,
    hour: Int = curHour,
    minute: Int = curMinute,
    second: Int = curSecond
): Long {
    val calendar = Calendar.getInstance()
    calendar.set(year, month - 1, day, hour, minute, second)
    calendar.set(Calendar.MILLISECOND, 0)
    return calendar.timeInMillis
}

/**
 * 获取第n天的时间戳
 * @param offset n
 * @return [Long] 时间戳
 */
fun getNextDate(offset: Int): Long {
    val calendar = Calendar.getInstance()
    calendar.time = Date(getDateFromYMD(curYear, curMonth, curDay))
    calendar.add(Calendar.DAY_OF_MONTH, offset)
    return calendar.timeInMillis
}

/**
 * 获取某个日子为标点的附近的日子时间戳
 * @receiver Long
 * @param offset Int
 * @return Long
 */
fun Long.getNextDay(offset: Int): Long {
    val calendar = Calendar.getInstance()
    calendar.time = Date(this)
    calendar.add(Calendar.DAY_OF_MONTH, offset)
    return calendar.timeInMillis
}

/**
 * 获取某个月时间戳
 * @receiver Long
 * @param offset Int
 * @return Long
 */
fun Long.getNextMonth(offset: Int): Long {
    val calendar = Calendar.getInstance()
    calendar.time = Date(this)
    calendar.add(Calendar.MONTH, offset)
    return calendar.timeInMillis
}

/**
 * 获取指定月份的天数
 * @param year 年
 * @param month 月
 * @return [Int] 天数
 */
@SuppressLint("SimpleDateFormat")
fun getDaysOfMonth(year: Int, month: Int): Int {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, month - 1)
    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
}

/**
 * 获取今天星期几
 * @return [Int] [Calendar.SUNDAY]
 */
fun getCurWeek(): Int {
    return curTime.getDateWeek()
}

/**
 * 获取时间戳是星期几
 * @return [Int] [Calendar.SUNDAY]
 */
fun Long.getDateWeek(): Int {
    val calendar = Calendar.getInstance()
    calendar.time = Date(this)
    return calendar.get(Calendar.DAY_OF_WEEK)
}

/**
 * 时间戳是否为今天的
 * @receiver Long
 * @return Boolean
 */
fun Long.isToday(): Boolean {
    return getDateYear() == curYear && getDateMonth() == curMonth && getDateDay() == curDay
}

/**
 * 时间戳是否为昨天的
 * @receiver Long
 * @return Boolean
 */
fun Long.isYesterday(): Boolean {
    val yesterday = curTime.getNextDay(-1)
    return getDateYear() == yesterday.getDateYear() && getDateMonth() == yesterday.getDateMonth() && getDateDay() == yesterday.getDateDay()
}

/**
 * 时间戳是否为前天的
 * @receiver Long
 * @return Boolean
 */
fun Long.isBeforeYesterday(): Boolean {
    val before = curTime.getNextDay(-2)
    return getDateYear() == before.getDateYear() && getDateMonth() == before.getDateMonth() && getDateDay() == before.getDateDay()
}

/**
 * 时间戳是否为后天
 * @receiver Long
 * @return Boolean
 */
fun Long.isAfterTomorrow(): Boolean {
    val tomorrow = curTime.getNextDay(2)
    return getDateYear() == tomorrow.getDateYear() && getDateMonth() == tomorrow.getDateMonth() && getDateDay() == tomorrow.getDateDay()
}

/**
 * 时间戳是否调整为之前
 * @receiver Long
 * @return Boolean
 */
fun Long.isTomorrowAny():Boolean{
    val gap = curTime - this
    return gap>86400000
}

/**
 * 本地时间转化为UTC时间
 * @receiver Long
 * @return Long
 */
fun Long.toUTCDate(): Long {
    val calendar = Calendar.getInstance().apply {
        timeInMillis = this@toUTCDate
    }
    val zoneOffset = calendar.get(Calendar.ZONE_OFFSET)
    val dstOffset = calendar.get(Calendar.DST_OFFSET)
    calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset))
    return calendar.timeInMillis
}

/**
 * UTC时间转化为本地时间
 * @receiver Long
 * @return Long
 */
@SuppressLint("SimpleDateFormat")
fun Long.toLocalDate(): Long {
    val pattern = "yyyyMMddHHmmssSSS"
    val utcSdf = SimpleDateFormat(pattern).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    val utcD = utcSdf.parse(this.toDateStr(pattern)) ?: return 0L
    val localSdf = SimpleDateFormat(pattern).apply {
        timeZone = TimeZone.getDefault()
    }
    val localStr = localSdf.format(utcD.time)
    return localStr.toDateLong(pattern)
}

/**
 * UTC时间转化为指定timeZone时间
 * @receiver Long
 * @param timeZoneInt Int
 * @return Long
 */
@SuppressLint("SimpleDateFormat")
fun Long.toCustomDate(timeZoneInt: Int): Long {
    val pattern = "yyyyMMddHHmmssSSS"
    val utcSdf = SimpleDateFormat(pattern).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }
    val utcD = utcSdf.parse(this.toDateStr(pattern)) ?: return 0L
    val localSdf = SimpleDateFormat(pattern).apply {
        timeZone = TimeZone.getTimeZone("GMT" + (if (timeZoneInt >= 0) "+" else "") + timeZoneInt)
    }
    val localStr = localSdf.format(utcD.time)
    return localStr.toDateLong(pattern)
}

fun Int.supplyDate():String{
    return if(this<10) StringBuilder("0").append(this).toString() else this.toString()
}