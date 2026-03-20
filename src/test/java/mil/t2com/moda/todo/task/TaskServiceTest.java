package mil.t2com.moda.todo.task;

import com.jayway.jsonpath.internal.function.PassthruPathFunction;
import mil.t2com.moda.todo.category.Category;
import mil.t2com.moda.todo.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    TaskService taskService;

    /// These are the actual "Tasks we're emulating"...
    Task learnMock;
    Task learnTdd;

    /// These are the actual "Categories" that we are wanting to have in our to-do application...
    Category started;
    Category finished;

    List<Task> tasks = new ArrayList<>();


    // Start using when refactoring
    @BeforeEach
    void setUp() {
        /// Arrange Refactor
        started = new Category("started important");
        learnMock = new Task("Learn about Mocks", "Learn about Inject mocks", false, started);
        learnMock.setId(1L);
        learnTdd = new Task("Learning TDD", "another one", true, finished);
    }

    @Test
    void shouldSaveNewTask() {
        // Arrange
        // Handled by the "BeforeEach()" above...

        // Act
        when(taskRepository.save(learnMock)).thenReturn(learnMock);

        Task result = taskService.saveTask(learnMock);

        // Assert
        assertThat(result.getId()).isEqualTo(1L);

        assertThat(result.getTitle()).isEqualTo("Learn about Mocks");

        assertThat(result.getCategory().getLabel()).isEqualTo("important");

        verify(taskRepository, only()).save(learnMock);
    }

    @Test
    void shouldSaveNewTaskWithNewCategory(){

        /// Here we're generating a new circumstance of creating a new Category with a new Task that needs to be tested!
        // Arrange
        // Handled by the "BeforeEach()" above...

        // Act
        when(categoryService.findTaskByLabel(started.getLabel())).thenReturn(Optional.of(started));
        when(taskRepository.save(learnMock)).thenReturn(learnMock);

        Task result = taskService.saveTask(learnMock);

//        verify(categoryService, only()).save(newCategory);
    }

    @Test
    void shouldGetAllTasksById(){
        // Arrange
        // Handled by the "BeforeEach()" above...

        // Act

        /// Here we are simulating the process of instantiating a new task List that will be filled with "tasks"
        tasks.addAll(List.of(learnMock, learnTdd));
        when(taskRepository.findAll()).thenReturn(tasks);

        List<Task> results = taskService.findAllTasks();

        // Assert
        assertThat(results).isEqualTo(tasks);
        verify(taskRepository, only()).findAll();

    }

    @Test
    void shouldFindTaskById(){

        // Arrange - handled by ForEach()

        // Act
        when(taskRepository.findById(1L)).thenReturn(Optional.of(learnMock));

        // Assert
        Task result = taskService.findTaskById(1L);

        verify(taskRepository, only()).findById(1L);
        assertThat(result).isEqualTo(learnMock);


    }

}