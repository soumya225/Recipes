package recipes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.services.RecipeService;
import recipes.models.Recipe;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
        Optional<Recipe> optionalRecipe = recipeService.findRecipeById(id);

        return optionalRecipe.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(optionalRecipe.get(), HttpStatus.OK);
    }

    @PostMapping("/recipe/new")
    public ResponseEntity<Map<String, Integer>> postRecipe(@Valid @RequestBody Recipe recipe) {
        recipeService.addRecipe(recipe);

        Map<String, Integer> map = new HashMap<>();
        map.put("id", recipe.getId());

        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @DeleteMapping("/recipe/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable int id) {
        try {
            recipeService.deleteRecipeById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
