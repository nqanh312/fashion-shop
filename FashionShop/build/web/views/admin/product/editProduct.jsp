<%-- 
    Document   : products
    Created on : Jul 1, 2022, 3:32:42 PM
    Author     : LENOVO
--%>

<%@page import="model.product.Product"%>
<%@page import="model.product.Group"%>
<%@page import="model.product.State"%>
<%@page import="model.product.Category"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel='shortcut icon' href='assets/images/logo.png' />
        <title>Edit product</title>
        <%
            ArrayList<Group> groups = (ArrayList<Group>) request.getAttribute("group");
            ArrayList<Category> listCategory = (ArrayList<Category>) request.getAttribute("listCategory");
            ArrayList<State> states = (ArrayList<State>) request.getAttribute("states");
            Product product = (Product) request.getAttribute("product");
        %>
    </head>
    <jsp:include page="../base/header.jsp" />
    <body>
        <div class="flex">
            <div class="w-64  bg-gray-50">
                <jsp:include page="../base/navbar.jsp" />
            </div>
            <div class="w-full px-20 py-5">
                <div class="mb-6">
                    <nav class="flex" aria-label="Breadcrumb">
                        <ol class="inline-flex items-center space-x-1 md:space-x-3">
                            <li class="inline-flex items-center text-xl">
                                <a href="/admin" class="inline-flex items-center text-sm font-medium text-gray-700 hover:text-gray-900">
                                    <svg class="mr-2 w-4 h-4" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M10.707 2.293a1 1 0 00-1.414 0l-7 7a1 1 0 001.414 1.414L4 10.414V17a1 1 0 001 1h2a1 1 0 001-1v-2a1 1 0 011-1h2a1 1 0 011 1v2a1 1 0 001 1h2a1 1 0 001-1v-6.586l.293.293a1 1 0 001.414-1.414l-7-7z"></path></svg>
                                    Admin
                                </a>
                            </li>
                            <li class="inline-flex items-center text-xl">
                                <a href="/admin/products" class="inline-flex items-center text-sm font-medium text-gray-700 hover:text-gray-900">
                                    <svg class="w-6 h-6 text-xl" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clip-rule="evenodd"></path></svg>
                                    Products
                                </a>
                            </li>
                            <li aria-current="page">
                                <div class="flex items-center">
                                    <svg class="w-6 h-6 text-gray-400 text-xl" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clip-rule="evenodd"></path></svg>
                                    <span class="ml-1 text-sm font-medium text-gray-400 md:ml-2 dark:text-gray-500">Edit</span>
                                </div>
                            </li>
                        </ol>
                    </nav>
                </div>
                    <div class="w-full px-15 pt-10 items-center">
                        <div class="flex w-full">
                            <div>
                                <div class="flex justify-center items-center">
                                    <form action="/admin/products/edit" style="min-width: 500px" id="form-product-edit">
                                        <div class="mb-6">
                                            <input type="hidden" id="id" name="id" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                                        </div>
                                        <div id="showErrorForm" class="hidden">
                                            <div id="contentErrorForm" class="bg-red-100 rounded-lg py-5 px-6 mb-4 text-base text-red-700 mb-3" role="alert">
                                            </div>
                                        </div>
                                        <div id="showSuccessForm" class="hidden p-4 mb-4 text-sm text-green-700 bg-green-100 rounded-lg" role="alert">
                                            <span id="contentSuccessForm" class="font-medium"></span>
                                        </div>
                                        <div class="mb-6">
                                            <label for="group" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-400">Group</label>
                                            <select id="group" name="group" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                                                <option value="all">All</option>
                                                <c:forEach items="${groups}" var="group">
                                                    <option value="${group.getId()}">${group.getName()}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="mb-6">
                                            <label for="category" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-400">Category</label>
                                            <select id="category" name="category" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                                            </select>
                                        </div>
                                        <div class="mb-6">
                                            <label for="name" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300">Name</label>
                                            <input required type="text" id="name" name="name" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                                        </div>
                                        <div class="mb-6">
                                            <label for="brand" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300">Brand</label>
                                            <input required type="text" id="brand" name="brand" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                                        </div>
                                        <div class="mb-6">
                                            <label for="price" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300">Price</label>
                                            <input required type="number" id="price" step="0.01" name="price" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                                        </div>
                                        <div class="mb-6">
                                            <label for="quantity" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300">Quantity</label>
                                            <input required type="number" id="quantity" name="quantity" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                                        </div>
                                        <div class="mb-6">
                                            <label for="discount" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300">Discount</label>
                                            <input required type="number" id="discount" name="discount" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                                        </div>
                                        <div class="mb-6">
                                            <label for="state" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-400">State</label>
                                            <select id="state" name="state" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                                                <c:forEach items="${states}" var="state">
                                                    <option value="${state.getId()}">${state.getName()}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="mb-6">
                                            <input id="isSale" name="isSale" value="true" aria-describedby="isSale" type="checkbox" class="w-4 h-4 text-blue-600 bg-gray-100 rounded border-gray-300 focus:ring-blue-500">
                                            <label for="isSale" class="ml-3 text-sm font-medium text-gray-900 dark:text-gray-300">is sale</label>
                                        </div>
                                        <div class="mb-6">
                                            <label for="description" class="block mb-2 text-sm font-medium text-gray-900">Description</label>
                                            <textarea id="description" name="description" rows="4" class="block p-2.5 w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 focus:ring-blue-500 focus:border-blue-500" placeholder="Description..."></textarea>
                                        </div>
                                        <div class="mb-6 grid grid-cols-3 space-3">
                                            <c:forEach items="${product.images}" var="image">
                                                <div class="mb-4" id="image-item-${image.id}">
                                                    <div class="max-w-[150px] bg-white rounded-lg border border-gray-200 shadow-md">
                                                        <div class="flex justify-end px-4 pt-4">
                                                            <button id="dropdown-button-${image.id}" data-dropdown-toggle="dropdown-image-${image.id}" class="hidden sm:inline-block text-gray-500" type="button">
                                                                <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path d="M10 6a2 2 0 110-4 2 2 0 010 4zM10 12a2 2 0 110-4 2 2 0 010 4zM10 18a2 2 0 110-4 2 2 0 010 4z"></path></svg>
                                                            </button>
                                                            <div id="dropdown-image-${image.id}" class="hidden z-10 w-44 text-base list-none bg-white rounded divide-y divide-gray-100 shadow ">
                                                                <ul class="py-1" aria-labelledby="dropdown-image-${image.id}">
                                                                    <li>
                                                                        <button type="button" onclick="addDeleteImage(${image.id})" class="block py-2 px-4 text-sm text-red-600 hover:bg-gray-100">Delete</button>
                                                                    </li>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                        <div class="flex flex-col items-center">
                                                            <img class="mb-3 w-full h-full" src="/assets/images/product/${image.image}" alt="Product image"/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </c:forEach>
                                        </div>
                                        <div class="mb-6">
                                            <label class="block mb-2 text-sm font-medium text-gray-900" for="images">Upload image</label>
                                            <input id="images"  accept="image/png, image/jpeg, image/jpg" type="file" name="images" multiple multiple class="block w-full text-sm text-gray-900 bg-gray-50 rounded-lg border border-gray-300 cursor-pointer dark:text-gray-400 focus:outline-none focus:border-transparent" >
                                        </div>
                                        <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center">Update</button>
                                    </form>
                                </div>
                            </div>                       
                        </div>
                    </div>
            </div>
        </div>
        <jsp:include page="../base/footer.jsp" />
        <script>
            const imagesDelete = [];
            const addDeleteImage = (id) => {
            imagesDelete.push(id);
            console.log(imagesDelete)
                    $("#image-item-" + id).remove();
            }
            const categorys = [
            <%for (Category category : listCategory) {%>
            {
            id: <%=category.getId()%>,
                    name: "<%=category.getName()%>",
                    group: {
                    id: <%=category.getGroup().getId()%>,
                            name: "<%=category.getGroup().getName()%>",
                    }
            },
            <%}%>
            ]

                    const category = document.getElementById("category");
            for (var i = 0; i < categorys.length - 1; i++) {
            category.innerHTML += '<option value="' + categorys[i].id + '">' + categorys[i].name + '</option>'
            }

            $("#id").val(${product.id})
                    $("#category").val(${product.category.id}),
                    $("#name").val("${product.name}"),
                    $("#brand").val("${product.brand}"),
                    $("#price").val(${product.price}),
                    $("#quantity").val(${product.quantity}),
                    $("#discount").val(${product.discount}),
                    $("#state").val(${product.state.id}),
                    $("#description").val(`${product.description}`),
                    $("#isSale").prop('checked', ${product.isSale});
            $("#group").on("change", function(e){
            category.innerHTML = "";
            categorys.forEach(item => {
            if (item.group.id == $("#group").val() || $("#group").val() == "all"){
            category.innerHTML += '<option value="' + item.id + '">' + item.name + '</option>'
            }
            })
            })

                    $("#form-product-edit").on("submit", function(e){
            e.preventDefault();
            if (imagesDelete.length > 0){
            imagesDelete.forEach((item) => {
            $.ajax({
            method: "POST",
                    url: "/admin/products/image/delete",
                    data: {id: item},
            }).done(function (data) {

            })
            })
            }
            const new_data = {
            id: $("#id").val(),
                    category: $("#category").val(),
                    name: $("#name").val(),
                    brand: $("#brand").val(),
                    price: $("#price").val(),
                    quantity: $("#quantity").val(),
                    discount: $("#discount").val(),
                    state: $("#state").val(),
                    description: $("#description").val(),
                    isSale: $("#isSale").is(':checked'),
                    images: $("#images").val(),
            }
            console.log(new FormData(document.getElementById("form-product-edit")));
            $.ajax({
            method: "POST",
                    url: "/admin/products/edit",
                    miniType: "multipart/form-data",
                    data: new FormData(document.getElementById("form-product-edit")),
                    cache: false,
                    contentType: false,
                    processData: false,
            }).done(function (data) {
            if (data?.detailMessage) {
            $("#showSuccessForm").addClass("hidden")
                    $('#contentErrorForm').text(data?.detailMessage);
            $("#showErrorForm").removeClass("hidden")
            } else{
            $("#showErrorForm").addClass("hidden")
                    $('#contentSuccessForm').text("Update success")
                    $("#category-show-name").text("Edit success")
                    $("#showSuccessForm").removeClass("hidden")
                    location.reload();
            }
            })
            })
        </script>
    </body>
</html>
