package recipes.services;

import lombok.Data;
import org.springframework.stereotype.Service;
import recipes.models.Recipe;

@Service
@Data
public class RecipeService {

    private Recipe recipe;

}
