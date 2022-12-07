package dev.attendance

import dev.attendance.handler.AttendanceHandler
import dev.attendance.handler.EmployeeHandler
import org.springframework.web.reactive.function.server.coRouter


/**
 * [AttendanceMainApp] routes configuration
 *
 *  Configure routes for employees & attendance entities
 *
 * @author Halim
 */
fun routes(employeeHandler: EmployeeHandler, attendanceHandler: AttendanceHandler) = coRouter {

    "$APPLICATION_BASE_URL/employee".nest {
        GET("", employeeHandler::findAll)
        GET("/{id}", employeeHandler::find)
        POST("/save", employeeHandler::save)

        "/delete".nest {
            DELETE("", employeeHandler::deleteAll)
            DELETE("/{id}", employeeHandler::delete)
        }
    }


    "$APPLICATION_BASE_URL/login".nest {
        GET("", attendanceHandler::findAll)
        GET("/{deviceId}", attendanceHandler::findByEmployee)
        POST("/save", attendanceHandler::save)

        "/delete".nest {
            DELETE("", attendanceHandler::deleteAll)
            DELETE("/query", attendanceHandler::deleteByMonthAndYear)
            DELETE("/{deviceId}", attendanceHandler::deleteByEmployee)
        }
    }
}