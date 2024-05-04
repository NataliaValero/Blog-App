package com.example.blogapp.utils

import android.text.format.DateUtils
import java.util.Date

class DateFormatter {
    companion object {
        fun getTimeAgo(date: Date): String {
            val now = System.currentTimeMillis()
            val time = date.time
            val diff = now - time

            return when {
                diff < DateUtils.MINUTE_IN_MILLIS -> "just now"
                diff < DateUtils.HOUR_IN_MILLIS -> formatTimeAgo(diff / DateUtils.MINUTE_IN_MILLIS, "minute")
                diff < DateUtils.DAY_IN_MILLIS -> formatTimeAgo(diff / DateUtils.HOUR_IN_MILLIS, "hour")
                diff < DateUtils.WEEK_IN_MILLIS -> formatTimeAgo(diff / DateUtils.DAY_IN_MILLIS, "day")
                diff < DateUtils.YEAR_IN_MILLIS -> formatTimeAgo(diff / DateUtils.WEEK_IN_MILLIS, "week")
                else -> formatTimeAgo(diff / DateUtils.YEAR_IN_MILLIS, "year")
            }
        }

        private fun formatTimeAgo(timeDiff: Long, unit: String): String {
            return if (timeDiff == 1L) {
                "$timeDiff $unit ago"
            } else {
                "$timeDiff ${unit}s ago"
            }
        }
    }
}