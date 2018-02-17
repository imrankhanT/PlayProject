package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import dao.EmployeeDAO
import play.api.db.slick.DatabaseConfigProvider
import scala.concurrent.ExecutionContext.Implicits.global
import dao.EmployeeDAO
import dao.EmployeeDAO
import model.Employee
import play.api.libs.json.Json
import model.CustomRead._
import scala.concurrent.Future
import model.EmployeeDto
import play.api.data.Forms._
import play.api.data._
import play.api.data.format.Formats._

@Singleton
class HomeController @Inject() (employeeDao: EmployeeDAO)(cc: ControllerComponents) extends AbstractController(cc) {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def insertEmployee = Action.async { request: Request[AnyContent] =>
    request.body.asJson.map {
      c =>
        var emplyee: Employee = c.as[Employee];

        employeeDao.insert(emplyee).map(res =>
          Ok("Registerd scessfully........" + res))
    }.getOrElse(Future {
      Ok("Registration Fails..........")
    })
  }

  def getAllEmployee = Action.async {
    employeeDao.all().map(res =>
      Ok("Employee Lists.........." + res))

  }

  def login = Action.async { request: Request[AnyContent] =>

    request.body.asJson.map {
      emp =>
        var emplyee: EmployeeDto = emp.as[EmployeeDto];
        employeeDao.login(emplyee.email, emplyee.password).map(res =>
          Ok("Login Scessfully........" + emplyee.email))
    }.getOrElse(Future {
      Ok("Login Fails..........")
    })
  }

  def getEmployeeByEmail(email: String) = Action.async {
    employeeDao.findEmployeeByEmail(email).map(
      emp =>
        Ok("User Found........" + emp))

  }
}
