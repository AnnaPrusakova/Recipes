package recipes.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import recipes.entity.Recipe;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Integer> {

    Optional<List<Recipe>> findRecipesByCategoryIgnoreCaseOrderByDateDesc(String category);

    Optional<List<Recipe>> findAllByNameContainingIgnoreCaseOrderByDateDesc(String name);
}
