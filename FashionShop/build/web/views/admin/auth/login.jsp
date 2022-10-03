<%-- 
    Document   : login
    Created on : Jun 24, 2022, 4:35:38 PM
    Author     : LENOVO
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel='shortcut icon' href='assets/images/logo.png' />
        <title>Login Page</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <link rel="stylesheet" href="https://unpkg.com/flowbite@1.3.4/dist/flowbite.min.css" />
    </head>
    <body>
        <div class="w-full min-h-screen bg-white flex justify-center items-center">
            <div class="w-[600px] px-12 py-20 rounded-[10px]" style="box-shadow: rgba(100, 100, 111, 0.2) 0px 7px 29px 0px;">
                <form class="w-full" method="POST" action="/admin/login">
                    <div class="mb-10 text-center">
                        <h3 class="text-5xl">Admin login</h3>
                    </div>
                    <div class="mb-6 w-full">
                        <label for="username" class="block mb-2 text-sm font-medium text-gray-900">Your email</label>
                        <input type="text" name="username" id="username" class="w-full bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" placeholder="matq@gmail.com" required>
                    </div>
                    <div class="mb-6 w-full">
                        <label for="password" class="block mb-2 text-sm font-medium text-gray-900">Your password</label>
                        <input type="password" name="password" id="password" class="w-full bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5" required>
                    </div>

                    <div class="flex items-baseline justify-between mt-4">
                        <button class="px-6 py-2 mt-4 text-white bg-blue-600 rounded-lg hover:bg-blue-900">Login</button>
                        <div>
                            <a href="/login" class="text-sm text-blue-600 hover:underline">Login as customer</a>
                            <span>/</span>
                            <a href="/#" class="text-sm text-blue-600 hover:underline">Forgot password?</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <script src="https://unpkg.com/flowbite@1.3.4/dist/flowbite.js"></script>
    </body>
</html>
