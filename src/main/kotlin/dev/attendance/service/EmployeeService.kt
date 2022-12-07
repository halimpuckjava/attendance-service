package dev.attendance.service

import dev.attendance.model.Employee
import dev.attendance.repository.EmployeeRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.data.mongodb.core.*
import org.springframework.data.mongodb.core.query.Criteria.where
import org.springframework.data.mongodb.core.query.isEqualTo


/**
 * [EmployeeService] implementation of [EmployeeRepository] asynchronous methods
 *
 *@author Halim
 */
class EmployeeService(private val mongo: ReactiveFluentMongoOperations) : EmployeeRepository {

    override suspend fun save(employee: Employee): Employee {
        return when (find(employee.id)) {
            null -> mongo.insert<Employee>().oneAndAwait(employee)
            else -> mongo.update<Employee>().matching(where("id").isEqualTo(employee.id)).replaceWith(employee).withOptions(
                FindAndReplaceOptions.options().returnNew()
            ).findReplaceAndAwait()
        }
    }

    override suspend fun find(id: String): Employee? {
        return mongo.query<Employee>().matching(where("id").isEqualTo(id)).awaitFirstOrNull()
    }

    override suspend fun delete(id: String): Boolean {
        return mongo.remove<Employee>().matching(where("id").isEqualTo(id)).allAndAwait().deletedCount >= 0
    }

    override suspend fun deleteAll() {
        mongo.remove<Employee>().allAndAwait()
    }

    override fun findAll(): Flow<Employee> {
        return mongo.query<Employee>().flow()
    }
}