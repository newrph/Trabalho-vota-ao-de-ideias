<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Ideias</title>
    <link rel="stylesheet" href="css/style.css">
    <style>
        /* Estilo do botao de voltar sem acento */
        .btn-voltar { 
            display: inline-block; 
            background-color: #6c757d; 
            color: white; 
            text-decoration: none; 
            padding: 10px 15px; 
            border-radius: 5px; 
            font-weight: bold; 
            margin-bottom: 20px; 
            font-family: sans-serif;
        }
        .btn-voltar:hover { 
            background-color: #5a6268; 
        }
    </style>
</head>
<body>

<div class="container">

    <a href="inicio.jsp" class="btn-voltar"><- Voltar para o Inicio</a>

    <h1>Banco de Ideias</h1>

    <form action="IdeiaController" method="post" class="formulario">

        <input type="text" name="titulo" placeholder="Titulo da ideia" required>

        <textarea name="descricao" placeholder="Descreva sua ideia" required></textarea>

        <button type="submit">Enviar Ideia</button>

    </form>

</div>

</body>
</html>