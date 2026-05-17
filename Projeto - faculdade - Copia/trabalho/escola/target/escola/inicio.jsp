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