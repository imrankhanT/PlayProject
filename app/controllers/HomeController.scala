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
import model.CustomRead.EmployeeReads
import model.CustomRead.EmployeeWrites
import scala.concurrent.Future
import model.EmployeeDto

@Singleton
class HomeController @Inject() (employeeDao: EmployeeDAO)(cc: ControllerComponents) extends AbstractController(cc) {

  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  def insertEmployee = Action.async { request: Request[AnyContent] =>
    /* var requestBody = request.body
    Ok("The Body Content" + requestBody)*/
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
    /*  var employeeDto = request.body
    employeeDao.findEmployeeByEmail(employeeDto.email).map{
      emp => emp match{
        case Some(employee) => Ok("Employee Availabe")
        case None => Ok("")
      }
    }*/

    val res: Option[Unit] = request.body.asJson.map {
      emp =>
        var employee: Employee = emp.as[Employee];
        val emp: Employee = employeeDao.findEmployeeByEmail(employee.email).map {
          emp7 =>
            if (employee.password.equals(emp.password)) {
              Ok("Login Scessfully...." + employee.name)
            }
        }

    }
  }

  def getEmployeeByEmail(email: String) = Action.async {
    employeeDao.findEmployeeByEmail(email).map(
      emp =>
        Ok("User Found........" + emp))

  }

}
