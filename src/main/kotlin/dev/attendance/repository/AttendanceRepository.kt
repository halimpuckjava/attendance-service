package dev.attendance.repository

import dev.attendance.model.Attendance
import dev.attendance.model.Employee
import kotlinx.coroutines.flow.Flow


/**
 * Abstract repository (CRUD) for [Attendance] document
 *
 * all methods are asynchronous and respect the coroutines aspect
 *
 * @author Halim
 */
interface AttendanceRepository {

    suspend fun save(attendance: Attendance): Attendance

    suspend fun deleteByEmployee(id: String): Boolean

    suspend fun deleteByMonthAndYear(month: Int, year: Int): Boolean

    suspend fun deleteAll()

    fun findByEmployee(id: String): Flow<Attendance>

    fun findByMonthAndYear(month: Int, year: Int): Flow<Attendance>

    fun findAll(): Flow<Attendance>
}