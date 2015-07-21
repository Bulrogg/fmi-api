<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="fr">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Admin : Gestion des coupons</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link href="<c:url value="/css/main.css"/>" rel="stylesheet">
    </head>
    <body>


        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <a class="navbar-brand" href="#">Bouchon</a>
                </div>
                <div id="navbar" class="collapse navbar-collapse">
                    <ul class="nav navbar-nav">
                        <li class="active"><a href="#">Gestion coupon</a></li>
                        <li><a href="#about">Actions</a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <br/>

        <div class="container">
            <h1>ADMIN : Gestion des coupons</h1>
            <br/>
            <table class="table">
                <tr>
                    <th>Id</th>
                    <th>Nom</th>
                    <th>Réduction</th>
                    <th>Est utilisé</th>
                    <th>Autre champs pur back</th>
                </tr>
                <c:forEach var="coupon" items="${coupons}">
                    <tr>
                        <td>${coupon.id}</td>
                        <td>${coupon.nom}</td>
                        <td>${coupon.reduction}</td>
                        <td>${coupon.estUtilise}</td>
                        <td>${coupon.unAutreChampsPurBack}</td>
                    </tr>
                </c:forEach>
            </table>

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Ajouter un coupon</h3>
                </div>
                <div class="panel-body">
                    <spring:url value="/admin/addCoupon" var="addCouponUrl" />
                    <form:form  class="form-inline" method="post" modelAttribute="couponAddForm" action="${addCouponUrl}">
                        <div class="form-group">
                            <label for="nom">Nom : </label>
                            <form:input id="nom" cssClass="form-control" path="nom" type="text"/>
                        </div>
                        <div class="form-group">
                            <label for="reduction">Réduction : </label>
                            <form:input id="reduction" cssClass="form-control" path="reduction" type="text"/>
                        </div>
                        <div class="form-group">
                            <label for="estUtilise">Est utilisé : </label>
                            <form:select id="estUtilise" cssClass="form-control" path="estUtilise">
                                <form:option value="false" label="oui"/>
                                <form:option value="true" label="non"/>
                            </form:select>
                        </div>
                        <div class="form-group">
                            <label for="unAutreChampsPurBack">Autre : </label>
                            <form:input id="unAutreChampsPurBack" cssClass="form-control" path="unAutreChampsPurBack" type="text"/>
                        </div>

                        <button type="submit" class="btn btn-primary">Ajouter</button>
                    </form:form>
                </div>
            </div>

        </div>


        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

    </body>

</html>