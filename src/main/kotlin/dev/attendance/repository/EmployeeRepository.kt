package dev.attendance.repository

import dev.attendance.model.Employee
import kotlinx.coroutines.flow.Flow


/**
 * Abstract repository (CRUD) for [Employee] document
 *
 * all methods are asynchronous and respect the coroutines aspect
 *
 * @author Halim
 */
interface EmployeeRepository {

    suspend fun save(employee: Employee): Employee

    suspend fun find(id: String): Employee?

    suspend fun delete(id: String): Boolean

    suspend fun deleteAll()

    fun findAll(): Flow<Employee>
}