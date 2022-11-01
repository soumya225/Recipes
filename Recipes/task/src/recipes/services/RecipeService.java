package recipes.services;


import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.models.Recipe;
import recipes.repositories.RecipeRepository;

import java.util.Optional;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public void addRecipe(Recipe recipe) {
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

}
