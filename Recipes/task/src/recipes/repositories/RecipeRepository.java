package recipes.repositories;

import org.springframework.data.repository.CrudRepository;
import recipes.models.Recipe;

import java.util.List;

public interface RecipeRepository extends CrudRepository<Recipe, Integer> {

    List<Recipe> findByCategoryIgnoreCase(String category);

    List<Recipe> findByNameContainingIgnoreCase(String name);
}
