package recipes.services;


import lombok.Getter;
import org.springframework.stereotype.Service;
import recipes.models.Recipe;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class RecipeService {

    private final Map<Integer, Recipe> recipes;
    private int id;

    public RecipeService() {
        this.recipes = new HashMap<>();
        id = 0;
    }

    public int addRecipe(Recipe recipe) {
        id++;
        recipe.setId(id);
        recipes.put(id, recipe);
        return id;
    }

    public Map<Integer, Recipe> getRecipes() {
        return Collections.unmodifiableMap(recipes);
    }

}
