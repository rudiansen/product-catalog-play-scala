package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.i18n.{Lang, Langs, MessagesApi, MessagesProvider, MessagesImpl}
import models.Product

class Products @Inject()(cc: ControllerComponents, langs: Langs, messagesApi: MessagesApi) extends AbstractController(cc) {

  implicit val lang: Lang = langs.availables.head
  implicit val messagesProvider: MessagesProvider = {
    MessagesImpl(lang, messagesApi)
  }

  def list = Action { implicit request =>
    val products = Product.findAll
    Ok(views.html.products.list(products))
  }

  def show(ean: Long) = Action {implicit request =>

    Product.findByEan(ean).map { product =>
      Ok(views.html.products.details(product))
    }.getOrElse(NotFound)
  }

}
