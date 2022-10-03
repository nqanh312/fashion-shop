<%-- 
    Document   : orders
    Created on : Jul 4, 2022, 11:18:34 PM
    Author     : LENOVO
--%>


<%@page import="model.common.Pagination"%>
<%@page import="model.order.OrderState"%>
<%@page import="model.order.Order"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Orders Page</title>
    </head>
    <%
        ArrayList<Order> orders = (ArrayList<Order>) request.getAttribute("orders");
        ArrayList<OrderState> ordersStates = (ArrayList<OrderState>) request.getAttribute("ordersStates");
        Pagination pagination = (Pagination) request.getAttribute("pagination");
    %>
    <jsp:include page="../base/header.jsp" />
    <body>
        <div class="flex">
            <div>
                <jsp:include page="../base/navbar.jsp" />
            </div>
            <div class="w-full px-10 py-5">
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
                                    <span class="ml-1 text-sm font-medium text-gray-400 md:ml-2 dark:text-gray-500">Orders</span>
                                </div>
                            </li>
                        </ol>
                    </nav>
                </div>
            <div class="w-full px-20 py-5">
                <div class="mt-10">
                    <c:forEach items="${orders}" var="order">
                        <div class="overflow-hidden mb-4 border">
                            <div class="overflow-x-auto shadow-md sm:rounded-lg">
                                <div class="inline-block min-w-full align-middle">
                                    <div class="flex items-center p-4">
                                        <div >
                                            <span class="text-lg font-medium">${order.id}</span>
                                        </div>
                                        <div class="ml-10">
                                            <div>
                                                <span class="text-lg font-medium">Khách hàng: ${order.customer.first_name} ${order.customer.last_name}</span>
                                            </div>
                                            <div class="mt-2">
                                                <span class="text-lg font-medium">Email: ${order.customer.email}</span>
                                            </div>
                                        </div>
                                        <div class="ml-10">
                                            <div>
                                                <span class="text-lg font-medium">Phone: ${order.customer.phone}</span>
                                            </div>
                                            <div class="mt-2">
                                                <span class="text-lg font-medium">Địa chỉ: ${order.customer.address}</span>
                                            </div>
                                        </div>
                                        <div>
                                            <span class="text-lg font-medium ml-10">Thời gian: <fmt:formatDate pattern = "yyyy-MM-dd hh:mm:ss" value = "${order.created_at}"/></span>
                                        </div>
                                        <div class="ml-10">
                                            <span class="text-lg font-medium">Tổng tiền: <span class="text-blue-500" id="total-${order.id}">${order.total()}</span>
                                                <script>
                                                    var price = Number.parseInt($("#total-${order.id}").text());
                                                    price = price.toLocaleString('vi', {style: 'currency', currency: 'VND'});
                                                    $("#total-${order.id}").text(price);
                                                </script>
                                        </div>
                                        <div class="ml-auto flex items-center">
                                            <div class="mr-6">
                                                <div class="flex items-center">
                                                    <label class="block text-sm font-medium text-gray-900 mr-5">Trạng thái:</label>
                                                    <select onchange="onChangeOrderState(${order.id}, this.value)" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                                                        <c:forEach items="${orderStates}" var="orderState">
                                                            <c:choose>
                                                                <c:when test="${orderState.id==order.stateId}">
                                                                    <option value="${orderState.id}" selected>${orderState.name}</option>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <option value="${orderState.id}">${orderState.name}</option>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <div class="flex">
                                                <button onclick="openTable(${order.id})" class="mr-10 flex text-gray-500 hover:bg-gray-100" aria-expanded="false" type="button">
                                                    <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 6h16M4 12h16M4 18h16"></path></svg>
                                                </button>                                                
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <table class="hidden min-w-full divide-y divide-gray-200 table-fixed" id="table-order-${order.id}">
                                <thead class="bg-gray-100">
                                    <tr>
                                        
                                        <th scope="col" class="py-3 px-6 text-xs font-medium tracking-wider text-left text-gray-700 uppercase">
                                            id
                                        </th>
                                        <th scope="col" class="py-3 px-6 text-xs font-medium tracking-wider text-left text-gray-700 uppercase">
                                            product
                                        </th>
                                        <th scope="col" class="py-3 px-6 text-xs font-medium tracking-wider text-left text-gray-700 uppercase">
                                            quantity
                                        </th>
                                        <th scope="col" class="py-3 px-6 text-xs font-medium tracking-wider text-left text-gray-700 uppercase">
                                            price
                                        </th>
                                        <th scope="col" class="py-3 px-6 text-xs font-medium tracking-wider text-left text-gray-700 uppercase">
                                            discount
                                        </th>
                                        <th scope="col" class="py-3 px-6 text-xs font-medium tracking-wider text-left text-gray-700 uppercase">
                                            total
                                        </th>
                                        <th scope="col" class="py-3 px-6 text-xs font-medium tracking-wider text-left text-gray-700 uppercase">
                                            real price
                                        </th>
                                    </tr>
                                </thead>
                                <tbody class="bg-white divide-y divide-gray-200" id="tbodyUser">
                                    <c:forEach items="${order.orderDetails}" var="orderDetail">
                                        <tr class="hover:bg-gray-100" id="user-item-${user.getId()}">
                                            
                                            <td class="py-4 px-6 text-sm font-medium text-gray-900 whitespace-nowrap">${orderDetail.id}</td> 
                                            <td class="py-4 px-6 text-sm font-medium text-gray-900 whitespace-nowrap">
                                                <a href="/products/detail?id=${orderDetail.product.id}">${orderDetail.product.name}</a>
                                            </td> 
                                            <td class="py-4 px-6 text-sm font-medium text-gray-900 whitespace-nowrap">${orderDetail.quantity}</td> 
                                            <td class="py-4 px-6 text-sm font-medium text-gray-900 whitespace-nowrap" id="price-${order.id}-${orderDetail.product.id}">${orderDetail.price}</td> 
                                    <script>
                                        var price = Number.parseInt($("#price-${order.id}-${orderDetail.product.id}").text());
                                        price = price.toLocaleString('vi', {style: 'currency', currency: 'VND'});
                                        $("#price-${order.id}-${orderDetail.product.id}").text(price);
                                    </script>
                                    <td class="py-4 px-6 text-sm font-medium text-gray-900 whitespace-nowrap">${orderDetail.discount}</td> 
                                    <td class="py-4 px-6 text-sm font-medium text-gray-900 whitespace-nowrap" id="total-${order.id}-${orderDetail.product.id}">${orderDetail.getTotal()}</td> 
                                    <script>
                                        var price = Number.parseInt($("#total-${order.id}-${orderDetail.product.id}").text());
                                        price = price.toLocaleString('vi', {style: 'currency', currency: 'VND'});
                                        $("#total-${order.id}-${orderDetail.product.id}").text(price);
                                    </script>
                                    <td class="py-4 px-6 text-sm font-medium text-gray-900 whitespace-nowrap" id="realprice-${order.id}-${orderDetail.product.id}">${orderDetail.getRealPrice()}</td>
                                    <script>
                                        var price = Number.parseInt($("#realprice-${order.id}-${orderDetail.product.id}").text());
                                        price = price.toLocaleString('vi', {style: 'currency', currency: 'VND'});
                                        $("#realprice-${order.id}-${orderDetail.product.id}").text(price);
                                    </script>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:forEach>
                </div>
                <div class="mt-10 w-full flex justify-center">
                    <nav aria-label="Page navigation example">
                        <ul class="inline-flex -space-x-px">
                            <li>
                                <a href="/admin/order?page=<%=pagination.getPrev()%>" data="<%=pagination.getPrev()%>" class="pagination-link py-2 px-3 ml-0 leading-tight text-gray-500 bg-white rounded-l-lg border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">Previous</a>
                            </li>
                            <c:if test="${pagination.getPageIndex()>2}">
                                <li>
                                    <a href="/admin/order?page=<%=pagination.getPageIndex() - 2%>" data="<%=pagination.getPageIndex() - 2%>" class="pagination-link py-2 px-3 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"><%=pagination.getPageIndex() - 2%></a>
                                </li>
                            </c:if>
                            <c:if test="${pagination.getPageIndex()>1}">
                                <li>
                                    <a href="/admin/order?page=<%=pagination.getPageIndex() - 1%>" data="<%=pagination.getPageIndex() - 1%>" class="pagination-link py-2 px-3 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"><%=pagination.getPageIndex() - 1%></a>
                                </li>
                            </c:if>
                            <li>
                                <a href="/admin/order?page=<%=pagination.getPageIndex()%>" data="<%=pagination.getPageIndex()%>" aria-current="page" class="pagination-link py-2 px-3 text-blue-600 bg-blue-50 border border-gray-300 hover:bg-blue-100 hover:text-blue-700 dark:border-gray-700 dark:bg-gray-700 dark:text-white"><%=pagination.getPageIndex()%></a>
                            </li>
                            <c:if test="${pagination.getPageIndex()<pagination.getCount()}">
                                <li>
                                    <a href="/admin/order?page=<%=pagination.getPageIndex() + 1%>" data="<%=pagination.getPageIndex() + 1%>" class="pagination-link py-2 px-3 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"><%=pagination.getPageIndex() + 1%></a>
                                </li>
                            </c:if>
                            <c:if test="${pagination.getPageIndex()+1<pagination.getCount()}">
                                <li>
                                    <a href="/admin/order?page=<%=pagination.getPageIndex() + 2%>" data="<%=pagination.getPageIndex() + 2%>" class="pagination-link py-2 px-3 leading-tight text-gray-500 bg-white border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white"><%=pagination.getPageIndex() + 2%></a>
                                </li>
                            </c:if>
                            <li>
                                <a href="/admin/order?page=<%=pagination.getNext()%>" data="<%=pagination.getNext()%>" class="pagination-link py-2 px-3 leading-tight text-gray-500 bg-white rounded-r-lg border border-gray-300 hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">Next</a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
        </div>
        <jsp:include page="../base/footer.jsp" />
        <script>
            function openTable(id) {
                var element = $("#table-order-" + id);
                if (element.hasClass("hidden")) {
                    element.removeClass("hidden");
                } else {
                    element.addClass("hidden");
                }
            }

            function onChangeOrderState(orderId, value) {
                $.ajax({
                    method: "POST",
                    url: "/admin/order",
                    data: {orderId: orderId, orderState: value}
                }).done(function (data) {

                })
            }
            const url_string = window.location.href;
            const url = new URL(url_string);
            const search = url.searchParams.get("q");
            const paginationLinks = document.querySelectorAll(".pagination-link");
            if (paginationLinks) {
                paginationLinks.forEach(item => {
                    var search = location.search.substring(1);
                    const params = search ? JSON.parse('{"' + decodeURI(search).replace(/"/g, '\\"')
                            .replace(/&/g, '","').replace(/=/g, '":"') + '"}') : {};
                    const page = item.getAttribute("data");
                    params.page = page;
                    const href = new URLSearchParams(params).toString();
                    item.setAttribute("href", "?" + href);
                })
            }

        </script>
    </body>
</html>
