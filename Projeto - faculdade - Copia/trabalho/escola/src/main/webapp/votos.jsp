<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    java.util.List<model.Ideia> lista = (java.util.List<model.Ideia>) request.getAttribute("listaIdeias");
    java.util.Map<Integer, java.util.List<String>> mapaComentarios = (java.util.Map<Integer, java.util.List<String>>) request.getAttribute("mapaComentarios");
    java.util.Map<Integer, Integer> mapaVotos = (java.util.Map<Integer, Integer>) request.getAttribute("mapaVotos");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Painel de Votacao</title>
    <style>
        .container { max-width: 800px; margin: 0 auto; padding: 20px; font-family: sans-serif; }
        h1, h2 { text-align: center; color: #333; }
        .card-voto { border: 1px solid #ddd; padding: 20px; margin-bottom: 20px; border-radius: 8px; background: #fff; max-width: 600px; margin-left: auto; margin-right: auto; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }
        .botoes-voto { display: flex; align-items: center; gap: 15px; margin-top: 15px; margin-bottom: 15px; }
        .positivo { background-color: #007bff; color: white; border: none; padding: 10px 25px; cursor: pointer; border-radius: 5px; font-weight: bold; font-size: 14px; }
        .positivo:hover { background-color: #0056b3; }
        .contador-votos { font-size: 15px; font-weight: bold; color: #28a745; }
        
        /* CSS dos comentarios */
        .secao-comentarios { border-top: 1px solid #eee; padding-top: 15px; margin-top: 15px; }
        .titulo-comentarios { font-size: 15px; font-weight: bold; color: #666; margin-bottom: 10px; }
        .lista-comentarios { list-style-type: none; padding-left: 0; margin-bottom: 15px; }
        .item-comentario { background: #f8f9fa; padding: 8px 12px; border-left: 3px solid #007bff; margin-bottom: 6px; border-radius: 0 4px 4px 0; font-size: 14px; color: #333; }
        .input-comentario { width: 72%; padding: 8px; border: 1px solid #ccc; border-radius: 4px; font-size: 14px; }
        .btn-comentar { background-color: #28a745; color: white; border: none; padding: 8px 15px; cursor: pointer; border-radius: 4px; font-weight: bold; font-size: 14px; }
        
        /* Estilo do botao de voltar */
        .btn-voltar { display: inline-block; background-color: #6c757d; color: white; text-decoration: none; padding: 10px 15px; border-radius: 5px; font-weight: bold; margin-bottom: 20px; }
        .btn-voltar:hover { background-color: #5a6268; }
    </style>
</head>
<body style="background-color: #f4f4f9;">

<div class="container">

    <a href="inicio.jsp" class="btn-voltar"><- Voltar para o Inicio</a>

    <h1>Painel de Votacao</h1>
    
    <%
        if (lista != null && !lista.isEmpty()) {
            for (model.Ideia ideia : lista) {
                int qtdVotos = (mapaVotos != null && mapaVotos.get(ideia.getId()) != null) ? mapaVotos.get(ideia.getId()) : 0;
    %>
                <div class="card-voto">
                    <h2><%= ideia.getTitulo() %></h2>
                    <p><%= ideia.getDescricao() %></p>

                    <div class="botoes-voto">
                        <form action="<%= request.getContextPath() %>/VotacaoController" method="POST" style="display:inline;">
                            <input type="hidden" name="ideia_id" value="<%= ideia.getId() %>">
                            <input type="hidden" name="acao_voto" value="votar">
                            <button type="submit" class="positivo">Gostar da Ideia</button>
                        </form>
                        <span class="contador-votos">Votos: <%= qtdVotos %></span>
                    </div>

                    <div class="secao-comentarios">
                        <div class="titulo-comentarios">Comentarios sobre a ideia:</div>
                        
                        <ul class="lista-comentarios">
                            <%
                                java.util.List<String> comentarios = (mapaComentarios != null) ? mapaComentarios.get(ideia.getId()) : null;
                                if (comentarios != null && !comentarios.isEmpty()) {
                                    for (String texto : comentarios) {
                            %>
                                        <li class="item-comentario"><%= texto %></li>
                            <%
                                    }
                                } else {
                            %>
                                    <li style="color: #999; font-style: italic; font-size: 13px;">Ninguem comentou ainda. Deixe sua opiniao!</li>
                            <%
                                }
                            %>
                        </ul>

                        <form action="<%= request.getContextPath() %>/VotacaoController" method="POST">
                            <input type="hidden" name="ideia_id" value="<%= ideia.getId() %>">
                            <input type="text" name="texto_comentario" class="input-comentario" placeholder="Escreva um comentario..." required>
                            <button type="submit" class="btn-comentar">Enviar</button>
                        </form>
                    </div>

                </div>
    <%
            }
        } else {
    %>
            <p style="text-align: center; color: #666; font-style: italic; margin-top: 30px;">
                Nenhuma ideia disponivel para votacao no momento no banco de dados.
            </p>
    <%
        }
    %>

</div>

</body>
</html>