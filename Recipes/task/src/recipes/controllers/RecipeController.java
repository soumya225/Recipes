package recipes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import recipes.exceptions.NotFoundException;
import recipes.exceptions.UnauthorizedException;
import recipes.services.RecipeService;
import recipes.models.Recipe;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable int id) {
        Optional<Recipe> optionalRecipe = recipeService.findRecipeById(id);

        return optionalRecipe.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(optionalRecipe.get(), HttpStatus.OK);
    }

    @PostMapping("/new")
    public ResponseEntity<Map<String, Integer>> postRecipe(@Valid @RequestBody Recipe recipe, Authentication auth) {
        recipeService.addRecipe(recipe, auth.getName());

        Map<String, Integer> map = new HashMap<>();
        map.put("id", recipe.getId());

        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable int id, Authentication auth) {
        try {
            recipeService.deleteRecipeById(id, auth.getName());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRecipe(@PathVariable int id, @Valid @RequestBody Recipe recipe, Authentication auth) {
        try {
            recipeService.updateRecipe(id, recipe, auth.getName());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (UnauthorizedException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/search")
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
