package dev.attendance.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("employees")
data class Employee(
    @Id val id: String,
    val firstName: String,
    val lastName: String,
    val function: String? = null,
    val service: String? = null
)
