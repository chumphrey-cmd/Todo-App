package mil.t2com.moda.todo.category;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    // public constructor method here. Allows our CategorServiceTest to call the categoryRepository as needed...
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category saveNewCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Listed as Optional because a to-do item may or may not have an id attached to it...
    // The logic below will handle the conditions in the event that a task does not have an id.
    public Optional <Category> findTaskByLabel(String label){
        return categoryRepository.findByLabel(label);
    }

    /// Security NOTE: ask Curt about the security of methods only being tied to Service and not Controller!!!!
    public Category createCategoryIfItDoesNotExist(String label) {

        /// 1B
        Optional <Category> queriedCategory = categoryRepository.findByLabel(label);

        /// 2B - Here we are just touching the service because there are a lot of additional tests we can do (e.g., removing white space, capitalization, special characters, etc.)
        /// See if I can complete the logic for this...

        if (queriedCategory.isEmpty()){
            return categoryRepository.save(new Category(label));
        }

        /// 3B
        return queriedCategory.get();
    }
    // ADD with Tests for: GetById, Put, Delete

}
