package com.project.mypocketpurse.service;

import com.project.mypocketpurse.dto.CategoryDTO;
import com.project.mypocketpurse.exception.MyDeleteException;
import com.project.mypocketpurse.model.Category;
import com.project.mypocketpurse.model.User;
import com.project.mypocketpurse.repository.CategoryRepository;
import com.project.mypocketpurse.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CategoryService
        extends GenericService<Category, CategoryDTO> {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository,
                           UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Category update(Category object) {
        return categoryRepository.save(object);
    }

    @Override
    public Category updateFromDTO(CategoryDTO object, Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category with such ID: " + id + " not found"));
        category.setName(object.getName());
        category.setDescription(object.getDescription());
        return categoryRepository.save(category);
    }

    @Override
    public Category createFromDTO(CategoryDTO newDtoObject) {
        Category newCategory = new Category();
        User user = userRepository.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        newCategory.setName(newDtoObject.getName());
        newCategory.setDescription(newDtoObject.getDescription());
        newCategory.setCreatedBy(user.getLogin());
        newCategory.setCreatedWhen(LocalDateTime.now());
        return categoryRepository.save(newCategory);
    }

    @Override
    public Category createFromEntity(Category newObject) {
        return categoryRepository.save(newObject);
    }

    @Override
    public void delete(Long objectId) throws MyDeleteException {
        Category category = categoryRepository.findById(objectId)
                .orElseThrow(() -> new NotFoundException("Category with such ID: " + objectId + " not found"));
        if (category.getActions().size() == 0) {
            categoryRepository.delete(category);
        } else {
            throw new MyDeleteException("Category can't be deleted");
        }
    }

    @Override
    public Category getOne(Long objectId) {
        return categoryRepository.findById(objectId)
                .orElseThrow(() -> new NotFoundException("Category with such ID: " + objectId + " not found"));
    }

    @Override
    public List<Category> listAll() {
        return categoryRepository.findAll();
        }
}
