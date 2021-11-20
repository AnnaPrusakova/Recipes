package recipes.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import recipes.entity.Recipe;
import recipes.entity.User;
import recipes.service.AuthenticationService;
import recipes.service.RecipeService;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/recipe")
@RequiredArgsConstructor
@Validated
public class Controller {
    private final RecipeService recipeService;
    private final AuthenticationService authenticationService;

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Integer id) {
        Recipe recipe = recipeService.getRecipeById(id);
        return ResponseEntity.ok(recipe);
    }

    @PostMapping("/new")
    public ResponseEntity<Map<String, Integer>> addRecipe(@Valid @RequestBody Recipe recipeRequest, Principal principal) {
        User user = authenticationService.findUserByEmail(principal.getName());
        recipeRequest.setUser(user);
        Recipe recipe = recipeService.saveRecipe(recipeRequest);
        return ResponseEntity.ok(Collections.singletonMap("id", recipe.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable Integer id, Principal principal) {
        recipeService.deleteRecipe(id,principal);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable @Min(1) Integer id,
                                               @Valid @RequestBody Recipe recipe, Principal principal) {
        Recipe update = recipeService.updateRecipe(id,recipe, principal);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(update);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> searchRecipe(@RequestParam(required = false) String category,
                                                     @RequestParam(required = false, name = "name") String name) {

        List<Recipe> recipeList = recipeService.searchRecipe(category,name);
        return ResponseEntity.ok(recipeList);
    }
}
