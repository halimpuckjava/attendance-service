package dev.attendance.model

import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Attendance(
    val deviceId: String,
    var employee: Employee? = null,
    val time: LocalDateTime
)
