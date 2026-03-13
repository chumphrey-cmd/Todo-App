package mil.t2com.moda.todo.task;

import mil.t2com.moda.todo.category.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    TaskRepository taskRepository;

    @Test
    void shouldSaveANewTask() {
        // Arrange
        Category newCategory = new Category("important");
        Task newTask = new Task("Learn tdd", "Remember to use this pattern; Red, green, refactor", false, newCategory);

        // Act
        Task savedNewTask = taskRepository.save(newTask);
        Optional<Task> result = taskRepository.findById(savedNewTask.getId());

        // Assert
        assertEquals("Learn tdd", result.get().getTitle());
        assertThat(result.get().getDescription()).isEqualTo(newTask.getDescription());

        // Add category value test

        /// Here we added the ".getLabel()" because previously it was just extracting only the "newTask.getCategory()".
        /// We needed to dive into the "Category" object to extract the label of "important"
        assertThat(result.get().getCategory().getLabel()).isEqualTo(newTask.getCategory().getLabel());
        assertThat(result.get()).isEqualTo(newTask);
    }
}