package recipes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import recipes.services.RecipeService;
import recipes.models.Recipe;

@RestController
@RequestMapping("/api")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe")
    public Recipe getRecipe() {
        return recipeService.getRecipe();
    }

    @PostMapping("/recipe")
    public void postRecipe(@RequestBody Recipe recipe) {
        recipeService.setRecipe(recipe);
    }
}
