package mil.t2com.moda.todo.task;

import jakarta.transaction.Transactional;
import mil.t2com.moda.todo.category.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional /// Unique to integration testing... because we don't want to actually build the DB
@AutoConfigureMockMvc
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskService taskService;

    // Setup test objects
    Category started;
    Task learnTdd;

    @BeforeEach
    // Use when something across files are repeatable!
    void setUp() {
        started = new Category("started");
        learnTdd = new Task( "Learn TDD", "research TDD", false, started);
    }

    @Test
    public void shouldCreateTask() throws Exception {
        String learnTddJson = objectMapper.writeValueAsString(learnTdd);

        /// Doing a request here and passing it as a result!
        ///  Now we are saving the tasks...
        MvcResult savedTask = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(learnTddJson))
                .andReturn();
        String expectedType = savedTask.getRequest().getContentType();
        Task expectedTask = objectMapper.readValue(savedTask.getResponse().getContentAsString(), Task.class);

        assertEquals(expectedType, "application/json");

        /// Fix here: we modified from learnTddJson to learnTdd which is a created instance of Task.
        assertEquals(expectedTask.getTitle(), learnTdd.getTitle());
        assertEquals(expectedTask.getCategory().getLabel(), learnTdd.getCategory().getLabel());
    }

    @Test
    public void shouldGetAllTasks() throws Exception {
        taskService.saveTask(new Task("Task 1 TDD", "Task 1 TDD", false, started));
        taskService.saveTask(new Task("Task 2 TDD", "Task 2 TDD", false, started));

        // Assert
        mockMvc.perform(get("/api/v1/task"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                //.andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Learn TDD"))
                .andExpect(jsonPath("$[1].title").value("Practice TDD"))
                //.andExpect(jsonPath("$[0].category.id").value(1L))
                .andExpect(jsonPath("$[0].category.label").value("normal"))
                .andExpect(jsonPath("$[1].cetegory.label").value("important"));
                //.andExpect(jsonPath("$[1].id").value(2L))
    }

    @Test
    public void shouldGetTaskById() throws Exception {

        // Act
        Task savedTask = taskService.saveTask(new Task("blank task TDD", "none", false, new Category("failed")));


        // Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/task/" + savedTask.getId()))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value(""))
                //.andExpect(jsonPath("$.category.id").value(1L))
                .andExpect(jsonPath("$.category.label").value("not started"));
    }
}
