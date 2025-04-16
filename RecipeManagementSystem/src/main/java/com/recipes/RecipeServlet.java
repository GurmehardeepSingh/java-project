package com.recipes;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class RecipeServlet extends HttpServlet {

    // Handles both displaying recipe list and individual recipe details
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        // Get the 'name' parameter to check if a recipe is clicked
        String recipeName = request.getParameter("name");

        if (recipeName != null) {
            // Display the details of the clicked recipe
            displayRecipeDetails(recipeName, out);
        } else {
            
            displayRecipeList(out);
        }
    }

    // Method to display the list of recipes
    private void displayRecipeList(PrintWriter out) {
        out.println("<html><body>");
        out.println("<h2>Recipe List (from XML)</h2>");
        out.println("<ul>");

        try {
            // Get path to XML file
            String xmlFilePath = getServletContext().getRealPath("/recipes.xml");

            File xmlFile = new File(xmlFilePath);

            // DOM parser
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            NodeList recipeList = doc.getElementsByTagName("recipe");

            for (int i = 0; i < recipeList.getLength(); i++) {
                Node node = recipeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element recipe = (Element) node;
                    String name = recipe.getElementsByTagName("name").item(0).getTextContent();
                    out.println("<li><a href='/your-web-app/recipes?name=" + name + "'>" + name + "</a></li>");
                }
            }
        } catch (Exception e) {
            out.println("<p>Error reading recipes: " + e.getMessage() + "</p>");
            e.printStackTrace(out);
        }

        out.println("</ul>");
        out.println("</body></html>");
    }

    // Method to display the details of a single recipe
    private void displayRecipeDetails(String recipeName, PrintWriter out) {
        out.println("<html><body>");
        out.println("<h2>Recipe Details: " + recipeName + "</h2>");

        try {
            // Get path to XML file
            String xmlFilePath = getServletContext().getRealPath("/recipes.xml");

            File xmlFile = new File(xmlFilePath);

            // DOM parser
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            NodeList recipeList = doc.getElementsByTagName("recipe");

            for (int i = 0; i < recipeList.getLength(); i++) {
                Node node = recipeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element recipe = (Element) node;
                    String name = recipe.getElementsByTagName("name").item(0).getTextContent();

                    if (name.equalsIgnoreCase(recipeName)) {
                        String ingredients = recipe.getElementsByTagName("ingredients").item(0).getTextContent();
                        String instructions = recipe.getElementsByTagName("instructions").item(0).getTextContent();

                        out.println("<h3>Ingredients:</h3>");
                        out.println("<p>" + ingredients + "</p>");
                        out.println("<h3>Instructions:</h3>");
                        out.println("<p>" + instructions + "</p>");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            out.println("<p>Error reading recipe details: " + e.getMessage() + "</p>");
            e.printStackTrace(out);
        }

        out.println("<br><a href='/your-web-app/recipes'>Back to Recipe List</a>");
        out.println("</body></html>");
    }
}
