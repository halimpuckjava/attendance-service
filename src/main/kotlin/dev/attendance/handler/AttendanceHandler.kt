package dev.attendance.handler

import dev.attendance.model.Attendance
import dev.attendance.model.Employee
import dev.attendance.service.AttendanceService
import dev.attendance.service.EmployeeService
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.*


/**
 * Router handler for asynchronous http requests
 *
 * @param employeeService: [EmployeeService] instance
 * @param service: [AttendanceService] instance
 *
 * @author Halim
 */
class AttendanceHandler(private val service: AttendanceService, private val employeeService: EmployeeService) {

    suspend fun save(request: ServerRequest): ServerResponse {
        val attendance = request.awaitBodyOrNull<Attendance>()
        return attendance?.let { ok().bodyValueAndAwait(service.save(it)) } ?: noContent().buildAndAwait()
    }

    suspend fun findByEmployee(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("deviceId")
        return service.findByEmployee(id).map { it.apply { employee = employeeService.find(it.deviceId) } }
            .let { ok().bodyAndAwait(it) }
    }

    suspend fun deleteByEmployee(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("deviceId")
        return ok().bodyValueAndAwait(service.deleteByEmployee(id))
    }

    suspend fun deleteByMonthAndYear(request: ServerRequest): ServerResponse {
        val month = request.queryParam("month").orElse("0")
        val year = request.queryParam("year").orElse("0")

        println(">>>>>> month: $month, year: $year")
        return service.deleteByMonthAndYear(month.toInt(), year.toInt()).let { ok().bodyValueAndAwait(it) }
    }

    suspend fun deleteAll(request: ServerRequest): ServerResponse {
        return service.deleteAll().let { ok().buildAndAwait() }
    }

    suspend fun findAll(request: ServerRequest): ServerResponse {
        return ok().bodyAndAwait(service.findAll().map { it.apply { employee = employeeService.find(it.deviceId) } })
    }
}