package dev.attendance.service

import dev.attendance.model.Attendance
import dev.attendance.repository.AttendanceRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.data.mongodb.core.*
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.isEqualTo
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Month


/**
 * [AttendanceService] implementation of [AttendanceRepository] asynchronous methods
 *
 *@author Halim
 */
class AttendanceService(private val mongo: ReactiveFluentMongoOperations) : AttendanceRepository {

    override suspend fun save(attendance: Attendance): Attendance {
        return mongo.insert<Attendance>().oneAndAwait(attendance)
    }

    override suspend fun deleteByEmployee(id: String): Boolean {
        return mongo.remove<Attendance>().matching(where("deviceId").isEqualTo(id)).allAndAwait().deletedCount >= 0
    }

    override suspend fun deleteByMonthAndYear(month: Int, year: Int): Boolean {
        val lastDayIndice = Month.of(month).length(true)
        val dateTmeMin = LocalDateTime.of(LocalDate.of(year, month, 1), LocalTime.of(0, 0, 0))
        val dateTmeMax = LocalDateTime.of(LocalDate.of(year, month, lastDayIndice), LocalTime.of(23, 59, 59))

        return mongo.remove<Attendance>().matching(where("time").gte(dateTmeMin).lte(dateTmeMax)).allAndAwait().deletedCount >= 0
    }

    override suspend fun deleteAll() {
        mongo.remove<Attendance>().allAndAwait()
    }

    override fun findByEmployee(id: String): Flow<Attendance> {
        return mongo.query<Attendance>().matching(where("deviceId").isEqualTo(id)).flow()
    }

    override fun findByMonthAndYear(month: Int, year: Int): Flow<Attendance> {
        val lastDayIndice = Month.of(month).length(true)
        val dateTmeMin = LocalDateTime.of(LocalDate.of(year, month, 1), LocalTime.of(0, 0, 0))
        val dateTmeMax = LocalDateTime.of(LocalDate.of(year, month, lastDayIndice), LocalTime.of(23, 59, 59))

        return mongo.query<Attendance>().matching(where("time").gte(dateTmeMin).lte(dateTmeMax)).flow()
    }

    override fun findAll(): Flow<Attendance> {
        return mongo.query<Attendance>().flow()
    }
}