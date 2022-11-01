package recipes.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {

    @JsonIgnore
    private int id;

    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private String[] ingredients;
    private String[] directions;

}
