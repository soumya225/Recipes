package recipes.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Recipe {

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

}
