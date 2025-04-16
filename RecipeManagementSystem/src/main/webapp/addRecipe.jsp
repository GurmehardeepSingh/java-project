<%@ page language="java" %>
<html>
<head>
    <title>Add New Recipe</title>
    <link rel="stylesheet" href="css/addRecipe.css">
</head>
<body>
    <h1>Add a New Recipe</h1>
    <form action="addRecipe" method="post">
        <label>Recipe Name:</label><br>
        <input type="text" name="name" required><br><br>

        <label>Image URL:</label><br>
        <input type="url" name="image" placeholder="Optional: Add image URL"><br><br>

        <label>Ingredients (separate by commas):</label><br>
        <input type="text" name="ingredients" required placeholder="e.g., Flour, Sugar, Salt"><br><br>

        <label>Instructions:</label><br>
        <textarea name="instructions" rows="5" cols="40" required placeholder="Write the recipe instructions here"></textarea><br><br>

        <input type="submit" value="Add Recipe">
    </form>
</body>
</html>
