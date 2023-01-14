package com.giftservice.gift_service.services;

import com.giftservice.gift_service.dao.CategoryDao;
import com.giftservice.gift_service.entities.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryDao categoryDao;

    public void createCategory (Category category) {
        categoryDao.save(category);
    }

    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    public Category findById(Long id) {
        return categoryDao.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found by id: " + id));
    }

    public void deleteById(Long id) {
        categoryDao.deleteById(id);
    }

    public void updateCategory(Long categoryId, Category newCategory) {
        Category category = categoryDao.findById(categoryId).get();
        category.setTitle(newCategory.getTitle());
        categoryDao.save(category);
    }
}