package recipes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.services.RecipeService;
import recipes.models.Recipe;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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

    @PutMapping("/recipe/{id}")
    public ResponseEntity<String> updateRecipe(@PathVariable int id, @Valid @RequestBody Recipe recipe) {
        try {
            recipeService.updateRecipe(id, recipe);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/recipe/search")
    public ResponseEntity<List<Recipe>> searchRecipes(@RequestParam(required = false) String category, @RequestParam(required = false) String name) {
        if((category == null && name == null) || (category != null && name != null)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<Recipe> results;

        if (category != null) {
            results = recipeService.searchByCategory(category);
        } else {
            results = recipeService.searchByName(name);
        }

        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
