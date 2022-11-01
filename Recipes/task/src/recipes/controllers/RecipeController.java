package recipes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.services.RecipeService;
import recipes.models.Recipe;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable int id) {
        Recipe recipeById = recipeService.getRecipes().get(id);

        return recipeById == null ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(recipeById, HttpStatus.OK);
    }

    @PostMapping("/recipe/new")
    public ResponseEntity<Map<String, Integer>> postRecipe(@RequestBody Recipe recipe) {
        recipeService.addRecipe(recipe);

        Map<String, Integer> map = new HashMap<>();
        map.put("id", recipe.getId());

        return new ResponseEntity<>(map,HttpStatus.OK);
    }
}
