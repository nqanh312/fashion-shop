<%-- 
    Document   : header
    Created on : June 24, 2022, 5:10:06 PM
    Author     : LENOVO
--%>
<%@page import="java.util.List"%>
<%@page import="model.auth.User"%>
<%@page import="model.product.Group"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="../base/headerImport.jsp" />
        <%
            ArrayList<Group> groups = (ArrayList<Group>) request.getAttribute("groups");
            User user = (User) session.getAttribute("user");
            Integer quantity = (Integer) session.getAttribute("quantity");
        %>
    </head>
    <body>
        <nav class="bg-white border-gray-200 px-2 sm:px-4 py-2.5 rounded">
            <div class="container flex flex-wrap justify-between items-center mx-auto">
                <a href="/" class="flex">
                    <img src="../assets/images/MATQn.png" class="mr-3 h-8" alt="Logo" />
                    <span class="self-center text-3xl font-semibold whitespace-nowrap dark:text-white">Shop 2hand</span>
                </a>
                <div class="flex items-center md:order-2">
                    <div class="mr-4 relative" id="carts">
                        <a href="/carts" class="flex mr-3 text-sm text-gray-700" id="user-menu-button" aria-expanded="false" type="button">
                            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z"></path></svg>
                        </a>
                        <span id="cart-quantity" class="<c:if test="${quantity==null || quantity==0}">hidden</c:if> bg-red-500 text-white text-sm font-semibold inline-flex items-center flex justify-center text-center rounded-full w-7 h-7 absolute -top-3 -right-2">
                            ${quantity}
                        </span>
                    </div>
                    <div class="mr-4 relative" id="carts">
                        <a href="/orders" class="flex mr-3 text-sm text-gray-700" id="user-menu-button" aria-expanded="false" type="button">
                            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 11V7a4 4 0 00-8 0v4M5 9h14l1 12H4L5 9z"></path></svg>                        
                        </a>
                    </div>
                    <c:if test="${user==null}">
                        <div class="flex items-center">
                            <a href="/login" class="block font-medium text-lg text-gray-700">Log in</a>
                            <span class="font-medium text-lg mx-3">/</span>
                            <a href="/signup" class="block font-medium text-lg text-gray-700">Sign up</a>
                            <span class="font-medium text-lg mx-3"></span>
                            <a href="/admin/login" class="mt-5 text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm px-3 py-2 text-center">I'am Admin</a>
                        </div>
                    </c:if>
                    <c:if test="${user!=null}">
                        <div>
                            <button type="button" class="flex mr-3 text-sm bg-gray-800 rounded-full md:mr-0 focus:ring-4 focus:ring-gray-300" id="user-menu-button" aria-expanded="false" type="button" data-dropdown-toggle="dropdown">
                                <span class="sr-only">Open user menu</span>
                                <c:if test="${user.getAvatar()!=null}">
                                    <img class="w-8 h-8 rounded-full" src="/assets/images/user/${user.avatar}" alt="user photo">
                                </c:if>
                                <c:if test="${user.getAvatar()==null}">
                                    <img class="w-8 h-8 rounded-full" src="/assets/images/default.png"/>
                                </c:if>

                            </button>
                            <!-- Dropdown menu -->
                            <div class="hidden z-50 my-4 text-base list-none bg-white rounded divide-y divide-gray-100 shadow" id="dropdown">
                                <div class="py-3 px-4">
                                    <span class="block text-sm text-gray-900">${user.username}</span>
                                </div>
                                <ul class="py-1" aria-labelledby="dropdown">
                                    <li>
                                        <a href="/profile" class="block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100">Profile</a>
                                    </li>
                                    <li>
                                        <a href="/orders" class="block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100">Order</a>
                                    </li>
                                    <li>
                                        <a href="/logout" class="block py-2 px-4 text-sm text-gray-700 hover:bg-gray-100">Logout</a>
                                    </li>
                                </ul>
                            </div>
                            <button data-collapse-toggle="mobile-menu-2" type="button" class="inline-flex items-center p-2 ml-1 text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200" aria-controls="mobile-menu-2" aria-expanded="false">
                                <span class="sr-only">Open main menu</span>
                                <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M3 5a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 10a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1zM3 15a1 1 0 011-1h12a1 1 0 110 2H4a1 1 0 01-1-1z" clip-rule="evenodd"></path></svg>
                                <svg class="hidden w-6 h-6" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M4.293 4.293a1 1 0 011.414 0L10 8.586l4.293-4.293a1 1 0 111.414 1.414L11.414 10l4.293 4.293a1 1 0 01-1.414 1.414L10 11.414l-4.293 4.293a1 1 0 01-1.414-1.414L8.586 10 4.293 5.707a1 1 0 010-1.414z" clip-rule="evenodd"></path></svg>
                            </button>
                        </div>
                    </c:if>

                </div>
            </div>
        </nav>
        <div class="bg-gray-100 w-full">
            <div class="flex justify-center items-center">
                <ul class="flex justify-center items-center flex-wrap" id="navGroupProduct">
                    <li class="relative">
                        <a href="/products" class="font-medium block py-4 px-6 nav-link hover:bg-gray-200 uppercase"><b>TẤT CẢ</b></a>
                    </li>
                    <c:forEach items="${groups}" var="group">
                        <c:choose>
                            <c:when test="${group.getCategories().size()>0}">
                                <li class="relative z-10" onmouseover="showMenuProduct(this)" onmouseout="hiddenMenuProduct(this)">
                                    <a href="/products?group=${group.getId()}" class="font-medium block py-4 px-10 nav-link hover:bg-gray-200 uppercase">${group.getName()}</a>
                                    <div class="transition duration-150 ease-in-out absolute top-[100%] left-[0] bg-gray-100 menuProductItem" style="display: none">
                                        <ul class="pt-2 px-2">
                                            <c:forEach items="${group.getCategories()}" var="category">
                                                <li class="font-medium border-b cursor-pointer" style="white-space: nowrap;">
                                                    <a href="/products?category=${category.getId()}" class="dropdown_menu-link block py-3 px-8 hover:bg-gray-200">${category.getName()}</a>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </div>
                                </li>
                            </c:when>
                            <c:otherwise>
                                <li class="relative">
                                    <a href="/products?group=${group.getId()}" class="font-medium block py-4 px-6 nav-link hover:bg-gray-200 uppercase">${group.getName()}</a>
                                </li>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </ul>
            </div>
        </div>

        <script>
            function showMenuProduct(element) {
                $(element).find(".menuProductItem").css("display", "block");
            }

            function hiddenMenuProduct(element) {
                $(element).find(".menuProductItem").css("display", "none");
            }
        </script>
        <jsp:include page="footerImport.jsp" />
    </body>
</html>
