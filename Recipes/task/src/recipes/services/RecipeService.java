package recipes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.exceptions.NotFoundException;
import recipes.exceptions.UnauthorizedException;
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

    public void addRecipe(Recipe recipe, String username) {
        recipe.setUser(username);
        recipe.setDate(LocalDateTime.now());
        recipeRepository.save(recipe);
    }

    public Optional<Recipe> findRecipeById(int id) {
        return recipeRepository.findById(id);
    }

    public void deleteRecipeById(int id, String username) throws NotFoundException, UnauthorizedException {
        Optional<Recipe> optionalRecipe = findRecipeById(id);

        if(optionalRecipe.isEmpty()) {
            throw new NotFoundException();
        } else if (!username.equals(optionalRecipe.get().getUser())) {
            throw new UnauthorizedException();
        } else {
            recipeRepository.deleteById(id);
        }
    }

    public void updateRecipe(int id, Recipe recipe, String username) throws NotFoundException, UnauthorizedException {
        Optional<Recipe> optionalRecipe = findRecipeById(id);

        if(optionalRecipe.isEmpty()) {
            throw new NotFoundException();
        } else if (!username.equals(optionalRecipe.get().getUser())) {
            throw new UnauthorizedException();
        } else {
            recipe.setId(id);
            recipe.setUser(username);
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
