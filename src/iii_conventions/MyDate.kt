package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int): Comparable<MyDate> {

    override fun compareTo(other: MyDate): Int = when {
        year > other.year -> 1
        year < other.year -> -1
        month > other.month -> 1
        month < other.month -> -1
        dayOfMonth > other.dayOfMonth -> 1
        dayOfMonth < other.dayOfMonth -> -1
        else -> 0
    }

    /*
Cleaner
when {
    year != other.year -> year - other.year
    month != other.month -> month - other.month
    else -> dayOfMonth - other.dayOfMonth
}

 */

    operator fun plus(interval: TimeInterval): MyDate {
        return this.addTimeIntervals(interval, 1)
    }

    operator fun plus(interval: RepeatedTimeInterval): MyDate {
        return this.addTimeIntervals(interval.interval, interval.number)
    }

}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)


enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class RepeatedTimeInterval(val interval: TimeInterval, val number: Int)

operator fun TimeInterval.times(number: Int) = RepeatedTimeInterval(this, number)


class DateRange(val start: MyDate, val endInclusive: MyDate): Iterable<MyDate> {

    operator fun contains(date: MyDate): Boolean = start <= date && date <= endInclusive

    override fun iterator(): Iterator<MyDate> = object: Iterator<MyDate> {
        var current = this@DateRange.start

        override fun hasNext(): Boolean = current <= this@DateRange.endInclusive

        override fun next(): MyDate {
            val result = current
            current = current.addTimeIntervals(TimeInterval.DAY, 1)
            return result
        }
    }
}
