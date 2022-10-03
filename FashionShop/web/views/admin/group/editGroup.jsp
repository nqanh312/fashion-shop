<%-- 
    Document   : editCategory
    Created on : Jun 25, 2022, 2:57:40 PM
    Author     : LENOVO
--%>

<%@page import="model.product.Group"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel='shortcut icon' href='assets/images/logo.png' />
        <title>Edit group</title>
        <%
            Group group = (Group) request.getAttribute("group");
        %>
    </head>
    <jsp:include page="../base/header.jsp" />
    <body>
        <div class="flex">
            <div class="w-64  bg-gray-50">
                <jsp:include page="../base/navbar.jsp" />
            </div>
            <div class="w-full px-10 py-5">
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
                                <a href="/admin/group" class="inline-flex items-center text-sm font-medium text-gray-700 hover:text-gray-900">
                                    <svg class="w-6 h-6 text-xl" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clip-rule="evenodd"></path></svg>
                                    Group
                                </a>
                            </li>
                            <li aria-current="page">
                                <div class="flex items-center">
                                    <svg class="w-6 h-6 text-gray-400 text-xl" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg"><path fill-rule="evenodd" d="M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z" clip-rule="evenodd"></path></svg>
                                    <span class="ml-1 text-sm font-medium text-gray-400 md:ml-2 dark:text-gray-500" id="category-show-name"><%=group.getName()%></span>
                                </div>
                            </li>
                        </ol>
                    </nav>
                </div>
                <div class="flex justify-center items-center" style="height: 80vh">
                    <form style="width: 500px" id="form-group-edit">
                        <div id="showErrorForm" class="hidden">
                            <div id="contentErrorForm" class="bg-red-100 rounded-lg py-5 px-6 mb-4 text-base text-red-700 mb-3" role="alert">
                            </div>
                        </div>
                        <div id="showSuccessForm" class="hidden p-4 mb-4 text-sm text-green-700 bg-green-100 rounded-lg" role="alert">
                            <span id="contentSuccessForm" class="font-medium"></span>
                        </div>
                        <input name="id" value="<%=group.getId()%>" type="hidden"/>
                        <div class="mb-6">
                            <label for="name" class="block mb-2 text-sm font-medium text-gray-900 dark:text-gray-300">Name category</label>
                            <input required type="text" id="name" name="name" value="<%=group.getName()%>"  class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                        </div>
                        <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center">Submit</button>
                    </form>
                </div>
            </div>
        </div>
        <script>
            $("#form-group-edit").on('submit', (e) => {
                e.preventDefault();
                if($("input[name='name']").val()===""){
                    $("#showSuccessForm").addClass("hidden")
                    $('#contentErrorForm').text("Value is empty");
                    $("#showErrorForm").removeClass("hidden")
                }else{
                    const data = {
                        id: $("input[name='id']").val(),
                        name: $("input[name='name']").val(),
                    }
           
                    $.ajax({
                        method: "POST",
                        url: "/admin/group/edit",
                        data: data,
                    }).done(function (data) {
                        if (data?.detailMessage) {
                            $("#showSuccessForm").addClass("hidden")
                            $('#contentErrorForm').text(data?.detailMessage);
                            $("#showErrorForm").removeClass("hidden")
                        } else{
                            $("#showErrorForm").addClass("hidden")
                            $('#contentSuccessForm').text("Update success")
                            $("#category-show-name").text(data?.name)
                            $("#showSuccessForm").removeClass("hidden")
                        }
                    })
                }
                
            })
       </script>
        <jsp:include page="../base/footer.jsp" />
    </body>
</html>
