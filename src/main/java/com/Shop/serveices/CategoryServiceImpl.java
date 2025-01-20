package com.Shop.serveices;

import com.Shop.entity.Category;
import com.Shop.repo.CategoryRepo;
import com.Shop.response.CategoryDto;
import com.Shop.response.Response;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

     private final CategoryRepo categoryRepo;

    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Response addCategory(String name) {
        Response response = new Response();
        Category category = new Category();
        category.setName(name);

        Category savedCat = categoryRepo.save(category);

        if (savedCat.getId() > 0) {
            response.setStatus(200);
            response.setMessage("Category added successful.");
        }else {
            response.setStatus(403);
            response.setMessage("Something went wrong, Retry.");
        }

        return response;
    }

    public List<CategoryDto> getAll(){
        List<Category> categories = categoryRepo.findAll();
        return  categories.stream().map(Category::getCatDto).collect(Collectors.toList());
    }
}
