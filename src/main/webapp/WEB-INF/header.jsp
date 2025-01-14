<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${pageTitle}</title>
    <style>

        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            display: flex;
            flex-direction: column;
            min-height: 100vh;
        }

        header {
            background-color: #333;
            color: white;
            padding: 10px 0;
            text-align: center;
        }

        header h1 {
            margin: 0;
        }

        nav {
            margin: 20px 0;
            text-align: center;
        }

        nav a {
            margin: 0 10px;
            color: #333;
            text-decoration: none;
        }

        nav a:hover {
            text-decoration: underline;
        }

        main {
            flex: 1;
            padding: 20px;
        }

        footer {
            background-color: #333;
            color: white;
            text-align: center;
            padding: 10px 0;
        }
    </style>
</head>
<body>
<header>
    <h1>My Web App</h1>
</header>
<nav>
    <a href="/">Home</a>
    <a href="/quests">Quests</a>
    <a href="/users">Users</a>
</nav>
<main>