<%@ page import="java.io.*, javax.xml.parsers.*, org.w3c.dom.*, javax.servlet.*, javax.servlet.http.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String recipeName = request.getParameter("name");

    String xmlFilePath = application.getRealPath("/recipes.xml");
    File xmlFile = new File(xmlFilePath);
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    Document doc = builder.parse(xmlFile);

    doc.getDocumentElement().normalize();
    NodeList recipeList = doc.getElementsByTagName("recipe");

    String ingredients = "";
    String instructions = "";
    String imageUrl = "";

    for (int i = 0; i < recipeList.getLength(); i++) {
        Node node = recipeList.item(i);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element recipe = (Element) node;
            String name = recipe.getElementsByTagName("name").item(0).getTextContent();

            if (name.equals(recipeName)) {
                ingredients = recipe.getElementsByTagName("ingredients").item(0).getTextContent();
                instructions = recipe.getElementsByTagName("instructions").item(0).getTextContent();
                if (recipe.getElementsByTagName("image").getLength() > 0) {
                    imageUrl = recipe.getElementsByTagName("image").item(0).getTextContent();
                }
                break;
            }
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Recipe: <%= recipeName %></title>
    <link rel="stylesheet" href="css/recipeDetails.css">
</head>
<body>
    <div class="container">
        <h1>Recipe: <%= recipeName %></h1>

        <% if (!imageUrl.isEmpty()) { %>
            <img src="<%= imageUrl %>" alt="<%= recipeName %>" class="recipe-image">
        <% } %>

        <h2 class="ingredients-title">Ingredients:</h2>
        <ul class="ingredients-list">
            <%
                String fixedIngredients = ingredients.replace("â€¢", "•").replace("â‚¹", "•");
                String[] ingredientList = fixedIngredients.split("-");
                for (String ingredient : ingredientList) {
                    if (!ingredient.trim().isEmpty()) {
                        out.println("<li>" + ingredient.trim() + "</li>");
                    }
                }
            %>
        </ul>

        <h2 class="instructions-title">Instructions:</h2>
        <p class="instructions"><%= instructions %></p>

        <a href="recipeList.jsp" class="back-btn">Back to Recipe List</a>
    </div>
</body>
</html>
