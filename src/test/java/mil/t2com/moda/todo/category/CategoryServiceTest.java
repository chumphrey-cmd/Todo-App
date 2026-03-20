package mil.t2com.moda.todo.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    // Pulling in a mocked or mirrored Repository response for Service so that we can simulate the transaction between Service and Repository...
    private CategoryRepository categoryRepository;

    @InjectMocks
    CategoryService categoryService;

    Category newCategory;

    // BeforeEach allows you to repeat duplicate tests that you've already made... If you start repeating yourself, use this annotation!
    @BeforeEach
    void setUp() {

        /// Arrange Refactor Across All Tests!
        newCategory = new Category("Delayed");
        newCategory.setId(1L);

    }

    @Test
    void shouldSaveNewCategory() {
        // Arrange
        // Handled by BeforeEach()

        // Act
        when(categoryRepository.save(newCategory)).thenReturn(newCategory);
        Category result = categoryService.saveNewCategory(newCategory);

        // Assert
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getLabel()).isEqualTo("Delayed");

        verify(categoryRepository, only()).save(newCategory);
    }

    @Test
    void shouldFindTaskByLabel() {

        // Arrange
        // Handled by BeforeEach()

        // Act
        when(categoryRepository.findByLabel(newCategory.getLabel())).thenReturn(Optional.of(newCategory));
        Optional<Category> result = categoryService.findTaskByLabel(newCategory.getLabel());

        // Assert
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getLabel()).isEqualTo("Delayed");
        verify(categoryRepository, only()).save(newCategory);

    }

    @Test
    void shouldCheckExistingCategoryAndSaveIfNotExists() {

//        // Arrange
//        Category createdCategory = new Category("not exists");
//        createdCategory.setId(3L);


        // Act

        ///  Interacting with the mocked Repository

        /// 1A
        when(categoryRepository.findByLabel(newCategory.getLabel())).thenReturn(Optional.empty());

        /// 2A
        when(categoryRepository.save(any(Category.class))).thenReturn(newCategory);

        /// 3A
        Category result = categoryService.createCategoryIfItDoesNotExist("Delayed");

        // Assert
        assertThat(result.getLabel()).isEqualTo("Delayed");

        verify(categoryRepository, times(1)).save(any(Category.class));
        verify(categoryRepository, times(1)).findByLabel("Delayed");

    }
}