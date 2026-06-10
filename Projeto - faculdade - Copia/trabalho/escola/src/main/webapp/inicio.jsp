<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Página Inicial</title>

    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container">
<button onclick="sessionStorage.clear(); localStorage.clear(); window.location.href='index.jsp';" style="
    background-color: #dc3545; 
    color: white; 
    border: none;
    padding: 10px 15px; 
    border-radius: 5px; 
    font-weight: bold; 
    font-family: sans-serif;
    cursor: pointer;
    margin-bottom: 20px;
">Sair</button>
    <h1>Sistema de Votação de Ideias</h1>

    <div class="menu">

        <a href="ideias.jsp">
            <button class="menu-btn">
                Ideias
            </button>
        </a>

        <a href="${pageContext.request.contextPath}/VotacaoController" style="text-decoration: none;">
    <button class="menu-btn">
        Votacao
    </button>
</a>

    </div>

</div>

</body>
</html>