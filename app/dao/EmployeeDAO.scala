package dao

import javax.inject.Inject

import slick.jdbc.MySQLProfile.api._
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfigProvider
import slick.jdbc.JdbcProfile
import scala.concurrent.ExecutionContext
import scala.concurrent.Future
import model.Employee

class EmployeeDAO @Inject() (protected val dbconfigur: DatabaseConfigProvider)(implicit exectionContext: ExecutionContext) {
  var dbConfig = dbconfigur.get[JdbcProfile]
  var db = dbConfig.db

  private val Employees = TableQuery[EmployeeTable]

  def insert(employee: Employee): Future[Int] = db.run(Employees += employee).map { count => (count) }

  def findEmployeeByEmail(email: String): Future[Option[Employee]] =
    db.run(Employees.filter(e => e.email === email).result.headOption)

  def login(email: String, password: String): Future[Option[Employee]] =
    db.run(Employees.filter(e => e.email === email && e.password === password).result.headOption)

  def all(): Future[Seq[Employee]] = db.run(Employees.result)

  private class EmployeeTable(tag: Tag) extends Table[Employee](tag, "Employee") {
    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def name = column[String]("name")
    def email = column[String]("email")
    def phone = column[String]("phone")
    def password = column[String]("password")
    def * = (id,name, email, phone, password) <> (Employee.tupled, Employee.unapply)
  }
}