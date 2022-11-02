package recipes.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recipe implements Comparable<Recipe> {

    @JsonIgnore
    @Id
    @GeneratedValue
    private int id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotEmpty
    @ElementCollection
    private List<String> ingredients;

    @NotEmpty
    @ElementCollection
    private List<String> directions;

    @NotBlank
    private String category;

    private LocalDateTime date;

    @Override
    public int compareTo(Recipe recipe) {
        return this.date.compareTo(recipe.getDate());
    }
}
