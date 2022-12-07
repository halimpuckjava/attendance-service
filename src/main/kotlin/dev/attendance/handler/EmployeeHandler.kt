package dev.attendance.handler

import dev.attendance.model.Employee
import dev.attendance.service.EmployeeService
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.*


/**
 * Router handler for asynchronous http requests
 *
 * @param service: [EmployeeService] instance
 *
 * @author Halim
 */
class EmployeeHandler(private val service: EmployeeService) {

    suspend fun save(request: ServerRequest): ServerResponse {
        val employee = request.awaitBodyOrNull<Employee>()
        return employee?.let { ok().bodyValueAndAwait(service.save(it)) } ?: noContent().buildAndAwait()
    }

    suspend fun find(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id")
        return service.find(id)?.let { ok().bodyValueAndAwait(it) } ?: notFound().buildAndAwait()
    }

    suspend fun delete(request: ServerRequest): ServerResponse {
        val id = request.pathVariable("id")
        return service.delete(id).let { ok().bodyValueAndAwait(it) }
    }

    suspend fun deleteAll(request: ServerRequest): ServerResponse {
        return service.deleteAll().let { ok().buildAndAwait() }
    }

    suspend fun findAll(request: ServerRequest): ServerResponse {
        return ok().bodyAndAwait(service.findAll())
    }
}