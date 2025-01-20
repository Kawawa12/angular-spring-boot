package com.Shop.serveices;

import com.Shop.entity.Category;
import com.Shop.response.CategoryDto;
import com.Shop.response.Response;

import java.util.List;

public interface CategoryService {

    Response addCategory(String name);

    List<CategoryDto> getAll();
}
