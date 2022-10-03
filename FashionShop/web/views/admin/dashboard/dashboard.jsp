<%-- 
    Document   : dashboard
    Created on : Jun 23, 2022, 8:23:46 PM
    Author     : LENOVO
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="model.order.Order"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.math.BigDecimal"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel='shortcut icon' href='assets/images/logo.png' />
        <title>Admin dashboard</title>
        <%
            ArrayList<Order> orders = (ArrayList<Order>) request.getAttribute("orders");
        %>
        <script src="https://cdn.jsdelivr.net/npm/chart.js@3.7.1/dist/chart.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.9.0/font/bootstrap-icons.css">
    </head>
    <jsp:include page="../base/header.jsp" />
    <body>
        <div class="flex">
            <div class="w-[230px]">
                <jsp:include page="../base/navbar.jsp" />
            </div>
            <div class="w-full p-5">
                <div class="flex justify-between">
                    <div class="p-8 m-2 text-lg text-blue-700 bg-blue-100 rounded-lg flex-1 shadow-sm" role="alert">
                        <div class="flex flex-col items-center">
                            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"></path></svg>
                            <span class="font-medium mt-3" id="quantity"></span> 
                        </div>
                    </div>
                    <div class="p-8 m-2 text-lg text-red-700 bg-orange-100 rounded-lg flex-1 shadow-sm" role="alert">
                        <div class="flex flex-col items-center">    
                            <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path></svg>
                            <span class="font-medium mt-3" id="total"></span> 
                        </div>
                    </div>
                </div>
                <div class="shadow-lg rounded-lg overflow-hidden">
                    
                    <canvas class="p-10" id="myChart"></canvas>
                </div>
            </div>
        </div>
    </body>
    <script>
        const labels = [
            'Jan',
            'Feb',
            'Mar',
            'Apr',
            'May',
            'Jun',
            'Jul',
            'Aug',
            'Sep',
            'Oct',
            'Nov',
            'Dec',
        ];

        const orders = [];
        const dataOrdersTotal = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
        const dataOrdersQuantity = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
        var quantity = 0;
        var total = 0;
        <c:forEach items="${orders}" var="order">
        var quantityOrder = 0;
        var totalOrder = 0;
        var order = {
            id: ${order.id},
            date: new Date("${order.created_at}"),
            month: new Date("${order.created_at}").getMonth() + 1,
            state: "${order.state.name}",
            orderDetails: [],
        }
        orders.push(order)
            <c:forEach items="${order.orderDetails}" var="orderDetail">
        total += ${orderDetail.getRealPrice()};
        quantity += ${orderDetail.quantity};
        totalOrder += ${orderDetail.getRealPrice()};
        quantityOrder += ${orderDetail.quantity};
        order.orderDetails.push({
            id: ${orderDetail.id},
            quantity: ${orderDetail.quantity},
            price: ${orderDetail.price},
            discount: ${orderDetail.discount},
            isSale: ${orderDetail.product.isSale},
            realPrice: ${orderDetail.getRealPrice()}
        })
            </c:forEach>
        dataOrdersTotal[new Date("${order.created_at}").getMonth()] += totalOrder;
        dataOrdersQuantity[new Date("${order.created_at}").getMonth()] += quantityOrder;
        var totalPrice = total;
        totalPrice = totalPrice.toLocaleString('vi', {style: 'currency', currency: 'VND'});
        $("#total").text(totalPrice);
        $("#quantity").text(quantity);
        </c:forEach>
        const data = {
            labels: labels,
            datasets: [
                {
                    label: 'Total Order',
                    data: dataOrdersTotal,
                    fill: true,
                    backgroundColor: "rgba(54, 162, 235, 0.2)",
                    borderColor: "rgb(54, 162, 235)",
                    pointBackgroundColor: "rgb(54, 162, 235)",
                    pointBorderColor: "#fff",
                    pointHoverBackgroundColor: "#fff",
                    pointHoverBorderColor: "rgb(54, 162, 235)",
                    stack: 'combined'
                }
            ]
        };

        const config = {
            type: 'line',
            data: data,
            options:
                    {
                        plugins: {
                            title: {
                                display: true,
                                text: 'Sales this year'
                            }
                        },
                        scales: {
                            y: {
                                stacked: true
                            }
                        }
                    },
        };



        const myChart = new Chart(
                document.getElementById('myChart'),
                config
                );
    </script>
    <jsp:include page="../base/footer.jsp" />
</html>
