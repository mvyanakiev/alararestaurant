package alararestaurant.repository;

import alararestaurant.domain.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findByName(String name);

    @Query(""+
            "SELECT c " +
            "FROM alararestaurant.domain.entities.Category c " +
            "ORDER BY size(c.items) DESC ")
    List<Category> exportCategoriesByCountOfItems();

}