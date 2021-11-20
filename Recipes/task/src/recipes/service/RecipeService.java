package recipes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import recipes.exception.IllegalRecipeParamException;
import recipes.entity.Recipe;
import recipes.exception.IllegalRequestException;
import recipes.exception.RecipeNotFoundException;
import recipes.repository.RecipeRepository;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;


    public Recipe getRecipeById(Integer id) {
        return recipeRepository.findById(id).orElseThrow(RecipeNotFoundException::new);
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public void deleteRecipe(Integer id, Principal principal){
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isEmpty()) {
            throw new RecipeNotFoundException();
        }
        String email = principal.getName();
        String recipeEmail = optionalRecipe.get().getUser().getEmail();
        if(!recipeEmail.equals(email)) {
            throw new IllegalRequestException("You can't delete the recipe");
        }
        recipeRepository.deleteById(id);
    }

    public Recipe updateRecipe(Integer id, Recipe recipe,Principal principal) {
        Optional<Recipe> optionalRecipe = recipeRepository.findById(id);
        if (optionalRecipe.isEmpty()) {
            throw new RecipeNotFoundException();
        }
        String email = principal.getName();
        String recipeEmail = optionalRecipe.get().getUser().getEmail();
        if(!recipeEmail.equals(email)) {
            throw new  IllegalRequestException("You can't update the recipe");
        }
        Recipe oldRecipe = optionalRecipe.get();
        oldRecipe.setName(recipe.getName());
        oldRecipe.setCategory(recipe.getCategory());
        oldRecipe.setDate(recipe.getDate());
        oldRecipe.setDescription(recipe.getDescription());
        oldRecipe.setIngredients(recipe.getIngredients());
        oldRecipe.setDirections(recipe.getDirections());

        return recipeRepository.save(oldRecipe);
    }

    public List<Recipe> searchRecipe(String category, String name) {
        if (category != null && name != null || category == null && name == null) {
            throw  new IllegalRecipeParamException();
        }
        Optional<List<Recipe>> optionalRecipe;
        if (category != null) {
            optionalRecipe = recipeRepository.findRecipesByCategoryIgnoreCaseOrderByDateDesc(category);
        } else {
            optionalRecipe = recipeRepository.findAllByNameContainingIgnoreCaseOrderByDateDesc(name);
        }
        return optionalRecipe.orElse(Collections.emptyList());
    }

}
