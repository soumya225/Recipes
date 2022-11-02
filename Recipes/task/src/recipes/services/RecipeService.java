package recipes.services;


import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.models.Recipe;
import recipes.repositories.RecipeRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public void addRecipe(Recipe recipe) {
        recipe.setDate(LocalDateTime.now());
        recipeRepository.save(recipe);
    }

    public Optional<Recipe> findRecipeById(int id) {
        return recipeRepository.findById(id);
    }

    public void deleteRecipeById(int id) throws IllegalArgumentException {
        Optional<Recipe> optionalRecipe = findRecipeById(id);

        if(optionalRecipe.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            recipeRepository.deleteById(id);
        }
    }

    public void updateRecipe(int id, Recipe recipe) throws IllegalArgumentException {
        Optional<Recipe> optionalRecipe = findRecipeById(id);

        if(optionalRecipe.isEmpty()) {
            throw new IllegalArgumentException();
        } else {
            recipe.setId(id);
            recipe.setDate(LocalDateTime.now());
            recipeRepository.save(recipe);
        }
    }

    public List<Recipe> searchByCategory(String category) {
        List<Recipe> results = recipeRepository.findByCategoryIgnoreCase(category);
        results.sort(Collections.reverseOrder());
        return results;
    }

    public List<Recipe> searchByName(String name) {
        List<Recipe> results = recipeRepository.findByNameContainingIgnoreCase(name);
        results.sort(Collections.reverseOrder());
        return results;
    }

}
