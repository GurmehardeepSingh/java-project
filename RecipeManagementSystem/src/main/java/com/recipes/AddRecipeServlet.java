package com.recipes;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class AddRecipeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String image = request.getParameter("image");
        String[] ingredients = request.getParameterValues("ingredients");
        String instructions = request.getParameter("instructions");

        String xmlPath = getServletContext().getRealPath("/recipes.xml");

        try {
            File xmlFile = new File(xmlPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc;

            if (xmlFile.exists()) {
                doc = dBuilder.parse(xmlFile);
            } else {
                doc = dBuilder.newDocument();
                Element rootElement = doc.createElement("recipes");
                doc.appendChild(rootElement);
            }

            doc.getDocumentElement().normalize();

            Element recipe = doc.createElement("recipe");

            Element nameElem = doc.createElement("name");
            nameElem.appendChild(doc.createTextNode(name));
            recipe.appendChild(nameElem);

            Element imageElem = doc.createElement("image");
            imageElem.appendChild(doc.createTextNode(image));
            recipe.appendChild(imageElem);

            Element ingredientsElem = doc.createElement("ingredients");
            for (String ing : ingredients) {
                Element ingElem = doc.createElement("ingredient");
                ingElem.appendChild(doc.createTextNode(ing));
                ingredientsElem.appendChild(ingElem);
            }
            recipe.appendChild(ingredientsElem);

            Element instructionsElem = doc.createElement("instructions");
            instructionsElem.appendChild(doc.createTextNode(instructions));
            recipe.appendChild(instructionsElem);

            doc.getDocumentElement().appendChild(recipe);

            // Save changes to the XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

            response.sendRedirect("recipeList.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error adding recipe.");
        }
    }
}
