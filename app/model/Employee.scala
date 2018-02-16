package model

case class Employee(id: Int, name: String, email: String, phone: String, password: String)

case class EmployeeDto(email: String, password: String)