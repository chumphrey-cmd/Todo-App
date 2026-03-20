package mil.t2com.moda.todo.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Example of a JPA word query
    // Optional<Task> findByCategoryOrId(Category category, Long id);

    // Getting all Tasks by Category
//    Optional<Task>
}
