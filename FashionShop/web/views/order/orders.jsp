<%-- 
    Document   : orders
    Created on : Jun 4, 2022, 11:18:34 PM
    Author     : LENOVO
--%>


<%@page import="model.order.Order"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel='shortcut icon' href='assets/images/logo.png' />
        <title>Orders Page</title>
    </head>
    <%
        ArrayList<Order> orders = (ArrayList<Order>) request.getAttribute("orders");
    %>
    <body>
        <jsp:include page="../base/header.jsp" />
        <div class="container mx-auto">
            <div class="mt-10">
                <c:if test="${orders.size() <= 0}">
                    <div class="w-full min-h-[50vh] flex justify-center items-center">
                        <p class="text-5xl">BẠN CHƯA CÓ ĐƠN HÀNG NÀO</p>
                    </div>
                </c:if>
                <c:forEach items="${orders}" var="order">
                    <div class="overflow-hidden mb-3">
                        <div class="overflow-x-auto shadow-md sm:rounded-lg">
                            <div class="inline-block min-w-full align-middle">
                                <div class="flex items-center p-4">
                                    <div>
                                        <span class="text-lg font-medium">Thời gian: <fmt:formatDate pattern = "yyyy-MM-dd hh:mm:ss" value = "${order.created_at}"/></span>
                                    </div>
                                    <div class="ml-10">
                                        <span class="text-lg font-medium">Tổng tiền: <span id="total-${order.id}">${order.total()}</span>
                                         <script>
                                            var price = Number.parseInt($("#total-${order.id}").text());
                                            price = price.toLocaleString('vi', {style: 'currency', currency: 'VND'});
                                            $("#total-${order.id}").text(price);
                                        </script>
                                    </div>
                                    <div class="ml-auto">
                                        <span class="text-lg font-medium text-red-500">Trạng thái: ${order.state.name}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <table class="min-w-full divide-y divide-gray-200 table-fixed">
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
        </div>
        <jsp:include page="../base/footerImport.jsp" />
    </body>
</html>
