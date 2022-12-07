package dev.attendance

import dev.attendance.handler.AttendanceHandler
import dev.attendance.handler.EmployeeHandler
import dev.attendance.service.AttendanceService
import dev.attendance.service.EmployeeService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.support.beans

@SpringBootApplication
class AttendanceMainApp

fun main() {
    runApplication<AttendanceMainApp> {
        addInitializers(
            beans {
                bean<EmployeeService>()
                bean<EmployeeHandler>()
                bean<AttendanceService>()
                bean<AttendanceHandler>()
                bean(::routes)
            }
        )
    }
}



