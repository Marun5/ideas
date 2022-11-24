package pl.Marcin.ideas.category.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.Marcin.ideas.category.domain.model.Category;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    @Query("from Category c order by size(c.questions) desc")
    List<Category> topCategories();

    List<Category> findAllByNameContainingIgnoreCase(String name);

    Page<Category> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
