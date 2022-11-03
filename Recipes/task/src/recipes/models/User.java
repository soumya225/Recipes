package recipes.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Entity
public class User {

    @Id
    @Pattern(regexp = "^[a-zA-Z\\d]+@[a-zA-Z\\d]+\\.[a-zA-Z\\d]+$")
    @NotBlank
    private String email;

    @Size(min = 8)
    @NotBlank
    private String password;
}
