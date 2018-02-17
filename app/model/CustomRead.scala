package model

import play.api.libs.json.Json

object CustomRead {
  implicit val EmployeeReads = Json.reads[Employee]
  implicit val EmployeeWrites = Json.writes[Employee]
  
  implicit val EmployeeDtoReads = Json.reads[EmployeeDto]
  implicit val EmployeeDtoWrites = Json.writes[EmployeeDto]
}