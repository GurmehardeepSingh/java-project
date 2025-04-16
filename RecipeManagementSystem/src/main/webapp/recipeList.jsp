<%@ page import="java.util.*, java.io.*, org.w3c.dom.*, javax.xml.parsers.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head>
    <title>Recipe List</title>
    <link rel="stylesheet" href="css/recipeList.css">
</head>
<body>
    <h1>Recipe List</h1>

    <a href="addRecipe.jsp" class="add-recipe-btn">Add Recipe</a>

    <ul>
        <%
            try {
                File xmlFile = new File(application.getRealPath("recipes.xml"));
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();

                NodeList nodeList = doc.getElementsByTagName("recipe");
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element element = (Element) node;
                        String name = element.getElementsByTagName("name").item(0).getTextContent();
                        String imageUrl = element.getElementsByTagName("image").item(0).getTextContent(); // Get image URL
        %>
                        <li>
                            <a href="recipeDetails.jsp?name=<%= name %>">
                                <img src="<%= imageUrl %>" alt="<%= name %>" class="recipe-image">
                                <div class="recipe-name"><%= name %></div>
                            </a>
                        </li>
        <%
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        %>
    </ul>
</body>
</html>
