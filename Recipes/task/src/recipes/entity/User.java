package recipes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = Access.WRITE_ONLY)
    private Integer id;

    @NotBlank
    @Email(regexp = "[a-zA-Z0-9_]+@[a-zA-Z0-9]+\\.[a-zA-Z]+")
    private String email;

    @NotBlank
    @Size(min = 8)
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    Set<Recipe> recipes;
}
