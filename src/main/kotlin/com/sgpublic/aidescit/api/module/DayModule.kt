package com.sgpublic.aidescit.api.module

import com.sgpublic.aidescit.api.core.spring.property.SemesterInfoProperty
import com.sgpublic.aidescit.api.result.SuccessResult
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

/**
 * 处理接口 [com.sgpublic.aidescit.api.controller.DayController]
 */
@Component
class DayModule {
    private lateinit var result: SuccessResult
    private var expired by Delegates.notNull<Long>()

    /**
     * 获取信息
     */
    fun get(): Map<String, Any> {
        return if (expired - APIModule.TS > 0){
            result
        } else {
            refresh()
        }
    }

    /**
     * 刷新信息
     */
    private fun refresh(): SuccessResult {
        val timeNow = Calendar.getInstance().timeInMillis
        val timeBetween = (timeNow - SemesterInfoProperty.START.timeInMillis) / 86400000
        val sdf = SimpleDateFormat.getDateInstance()

        SuccessResult(
            "day_count" to timeBetween,
            "date" to sdf.format(SemesterInfoProperty.START.time),
            "semester" to SemesterInfoProperty.SEMESTER,
            "school_year" to SemesterInfoProperty.YEAR,
            "evaluation" to SemesterInfoProperty.EVALUATION
        ).let {
            expired = APIModule.TS + 1
            result = it
            return it
        }
    }
}