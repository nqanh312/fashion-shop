<%-- 
    Document   : manageCategory
    Created on : Jun 25, 2022, 8:59:54 AM
    Author     : LENOVO
--%>

<%@page import="model.product.Group"%>
<%@page import="model.product.Category"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel='shortcut icon' href='assets/images/logo.png' />
        <title>Category Admin</title>
        <%
            ArrayList<Category> listCategory = (ArrayList<Category>) request.getAttribute("listCategory");
            ArrayList<Group> groups = (ArrayList<Group>) request.getAttribute("groups");
        %>
    </head>
    <jsp:include page="../base/header.jsp" />
    <body>
        <div class="flex">
            <div>
                <jsp:include page="../base/navbar.jsp" />
            </div>
            <div class="w-full px-20 py-5">
                <div class="mb-6">
                    <nav class="flex" aria-label="Breadcrumb">
                        <ol class="inline-flex items-center space-x-1 md:space-x-3">
                            <li class="inline-flex items-center text-xl">
                                <a href="/dashboard" class="inline-flex items-center text-sm font-medium text-gray-700 hover:text-gray-900">
                                    <svg class="mr-2 w-4 h-4" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M10.707 2.293a1 1 0 00-1.414 0l-7 7a1 1 0 001.414 1.414L4 10.414V17a1 1 0 001 1h2a1 1 0 001-1v-2a1 1 0 011-1h2a1 1 0 011 1v2a1 1 0 001 1h2a1 1 0 001-1v-6.586l.293.293a1 1 0 001.414-1.414l-7-7z"></path></svg>
                                    Dashboard
                                </a>
                            </li>
                            <li aria-current="page">
                                <div class="flex items-center">
                                    <svg class="w-6 h-6 text-gray-400 text-xl" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clip-rule="evenodd"></path></svg>
                                    <span class="ml-1 text-sm font-medium text-gray-400 md:ml-2 dark:text-gray-500">Category</span>
                                </div>
                            </li>
                        </ol>
                    </nav>
                </div>
                <div class="mb-6 flex items-center space-x-3 flex-col lg:flex-row">
                    <a href="/admin/category/add" class="inline-flex items-center py-2 px-4 text-sm font-medium text-center text-gray-900 bg-white rounded-lg border border-gray-300 hover:bg-gray-100 focus:ring-4 focus:ring-blue-300">Add Category</a>
                    <form form="/admin/event" method="GET" class="flex items-center flex-col lg:flex-row mt-2 lg:mt-0" id="form-search-category">
                        <div class="relative">
                            <div class="flex absolute inset-y-0 left-0 items-center pl-3 pointer-events-none">
                                <svg class="w-5 h-5 text-gray-500" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M8 4a4 4 0 100 8 4 4 0 000-8zM2 8a6 6 0 1110.89 3.476l4.817 4.817a1 1 0 01-1.414 1.414l-4.816-4.816A6 6 0 012 8z" clip-rule="evenodd"></path></svg>
                            </div>
                            <input type="text" name="q" id="search" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-80 pl-10 p-2.5" placeholder="Search for items">
                        </div>
                        <select id="group" name="group" class="ml-0 lg:ml-3 mt-3 lg:mt-0 min-w-[200px] bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                             <option value="-1">Group</option>
                            <c:forEach items="${groups}" var="group">
                                <option value="${group.getId()}">${group.getName()}</option>
                            </c:forEach>
                        </select>
                        <button type="submit" class="ml-0 lg:ml-3 mt-3 w-full  lg:mt-0 text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center">search</button>
                    </form>
                </div>
                <div class="w-full grid lg:grid-cols-3 xl:grid-cols-4 2xl:grid-cols-6 gap-4">
                    <c:forEach items="${listCategory}" var="category">
                        <div class="min-w-md bg-white rounded-lg border border-gray-200 shadow-md py-8 px-6">
                            <div class="flex flex-col items-center pb-5">
                                <h3 class="mb-3 text-xl font-medium text-gray-900 text-center">${category.getName()}</h3>
                                <h3 class="mb-1 text-md font-medium text-red-500">${category.getGroup().getName()}</h3>
                                <span class="text-sm text-gray-500">id - ${category.getId()}</span>
                                <div class="flex mt-4 space-x-3">
                                    <a href="/admin/category/edit?id=${category.getId()}" class="inline-flex items-center py-2 px-4 text-sm font-medium text-center text-gray-900 bg-white rounded-lg border border-gray-300 hover:bg-gray-100 focus:ring-4 focus:ring-blue-300">Edit</a>
                                    <a href="/admin/category/delete?id=${category.getId()}" class="inline-flex items-center py-2 px-4 text-sm font-medium text-center text-white bg-blue-700 rounded-lg hover:bg-blue-800 focus:ring-4 focus:ring-blue-300">Delete</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <jsp:include page="../base/footer.jsp" />
    </body>
</html>
