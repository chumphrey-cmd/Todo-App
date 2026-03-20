package mil.t2com.moda.todo.task;

import mil.t2com.moda.todo.category.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/// We modified the WebMvcTest from TaskControllerTest.class -> TaskController.test the problem was that we were loading a stub of WebMvcTest essentially loading itself on itself...
/// NOTE: When you create tests at the Controller level, you want to test the MOST common path.
///     For example: one path for user selection, one path that's empty, one path....

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    TaskService taskService;

    @Captor
    ArgumentCaptor<Task> captor = ArgumentCaptor.forClass(Task.class);
    List<ArgumentCaptor<Task>> captors = new ArrayList<>(List.of(ArgumentCaptor.forClass(Task.class)));

    /// These are the actual "Tasks we're emulating"...
    String enablement = "enablement";
    Task learnHttpMethods;
    Task learnCaptor;

    /// These are the actual "Categories" that we are wanting to have in our to-do application...
    Category enableCategory = new Category(enablement);
    Category studyCategory = new Category("study");

    List<Task> tasks = new ArrayList<>();

    @BeforeEach
    void setup(){
        // Arrange
        learnHttpMethods = new Task (
                "Learn about testing HTTP request/response",
                "Learn how to use WebMvcTest",
                false,
                enableCategory);

        learnHttpMethods.setId(1L);

        learnCaptor = new Task (
                "Learn Captor",
                "Learn how to use captor",
                false,
                studyCategory);

        when(taskService.saveTask(any(Task.class))).thenReturn(learnHttpMethods);
    }

    @Test
    void shouldSaveNewTask() throws Exception {
        // Arrange


        // Act
        mockMvc.perform(post("/api/v1/task")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(learnHttpMethods)))
                // result matchers
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(matchesPattern("Learn about request/response")))
                .andExpect(jsonPath("$.description").value(containsString("Learn how to")))
                .andExpect(jsonPath("$.category.label").value("immediate"))
                .andDo(print()
                );

        // Assert
        verify(taskService, times(1)).saveTask(any(Task.class));
    }

    @Test
    void shouldSaveNewTaskUsingCaptor() throws Exception {
        // Arrange
        // Refactored Above...

        when(taskService.saveTask(any(Task.class))).thenReturn(learnHttpMethods);

        // Act
        mockMvc.perform(post("/api/v1/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(learnHttpMethods)))
                // result matchers
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value(matchesPattern("Learn about request/response")))
                .andExpect(jsonPath("$.description").value(containsString("Learn how to")))
                .andExpect(jsonPath("$.category.label").value("immediate"))
                .andDo(print()
                );

        // Assert
        verify(taskService, only()).saveTask(captor.capture());
        assertThat(captor.getValue()).usingRecursiveComparison().isEqualTo(learnHttpMethods);

        verify(taskService, only()).saveTask(any(Task.class));
    }

    @Test
    void shouldGetAllTasksUsingCaptor() throws Exception{
        // Arrange
        // Refactored in the for each...

        tasks.addAll(List.of(learnHttpMethods, learnHttpMethods));
        when(taskService.findAllTasks()).thenReturn(tasks);

        // Act
        mockMvc.perform(get("/api/v1/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(learnHttpMethods)))
                // result matchers
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                /// Argument captor is very useful here because there can be a lot of JSON paths for your chose from!
                .andDo(print()
                );

        // Assert
        // In progress...
    }

}