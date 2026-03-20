package mil.t2com.moda.todo.task;

import mil.t2com.moda.todo.category.Category;
import mil.t2com.moda.todo.category.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryService categoryService;

    public TaskService(TaskRepository taskRepository, CategoryService categoryService) {
        this.taskRepository = taskRepository;
        this.categoryService = categoryService;
    }

    /// Here we are setting up the logic and method that accepts a Task parameter. Here we're going to set up the logic for the Task...
    public Task saveTask(Task task) {
        // Check Category Service to ensure that I exist!
        Optional<Category> existingCategory = categoryService.findTaskByLabel(task.getCategory().getLabel());

        if (existingCategory.isPresent()){

        }
        return taskRepository.save(task);
    }

    public List<Task> findAllTasks() {
        return taskRepository.findAll();
    }


    public Task findTaskById(Long id){
            return taskRepository.findById(id).orElseThrow();
    }
    // ADD with Tests for: GetById, Put, Delete

}
