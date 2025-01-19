package com.Shop.serveices;

import com.Shop.dto.ProductDto;
import com.Shop.entity.Category;
import com.Shop.entity.Product;
import com.Shop.repo.CategoryRepo;
import com.Shop.repo.ProductRepo;
import com.Shop.response.ProductResp;
import com.Shop.response.Response;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    public ProductServiceImpl(ProductRepo productRepo, CategoryRepo categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }


    @Override
    public Response addCategory(String name) {
        Response response = new Response();
        if(name != null) {
            Category category = new Category();
            category.setName(name);
            Category savedCat = categoryRepo.save(category);
            if(savedCat.getId() > 0) {
                response.setStatus(200);
                response.setMessage("Category Added Successful.");
            }else {
                response.setStatus(403);
                response.setMessage("Something went wrong, Retry.");
            }
        }
        return response;
    }

    @Override
    public Response addProduct(ProductDto productDto, MultipartFile imageFile) {

        Response response = new Response();
        // Check if category exists
        Optional<Category> category = categoryRepo.findById(productDto.getCatId());
        if (category.isEmpty()) {
             response.setStatus(403);
             response.setMessage("Category is not found.");
             return response;
        }

        // Save image and get path
        String imagePath = saveImage(imageFile);

        // Create product object from productDto
        Product product = new Product();
        product.setName(productDto.getName());
        product.setCategory(category.get());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(imagePath);

        Product savedProd = productRepo.save(product);

        if(savedProd.getId() > 0) {
            response.setStatus(200);
            response.setMessage("Product Added successful.");
        }else{
            response.setStatus(403);
            response.setMessage("Something went wrong! retry.");
        }

        return response;
    }

    @Override
    public Response updateProduct(ProductDto productDto, MultipartFile imageFile) {
        return null;
    }

    @Override
    public ProductResp getProducts() {
        return null;
    }

    @Override
    public ProductResp getProductById(Long prdId, Long catId) {
        ProductResp response = new ProductResp();
        Optional<Product> optionalProduct = productRepo.findById(prdId);
        Optional<Category> optionalCategory = categoryRepo.findById(catId);

        if(optionalProduct.isPresent() && optionalCategory.isPresent()){
            response.setId(optionalProduct.get().getId());
            response.setName(optionalProduct.get().getName());
            response.setPrice(optionalProduct.get().getPrice());
            response.setImageUrl(optionalProduct.get().getImageUrl());
            response.setCategoryName(optionalCategory.get().getName());

        }else{
            throw new RuntimeException("Product or category not found");
        }

        return response;
    }

    //A method to  return image path
    private String saveImage(MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty()) {
            return null; // Or handle as per the requirement
        }

        try {
            // Define the path where images will be saved
            String directory = "src/main/resources/static/images/";
            File folder = new File(directory);
            if (!folder.exists()) {
                folder.mkdirs(); // Create directories if they don't exist
            }

            // Create the file path with the image's original filename
            Path path = Path.of(directory, Objects.requireNonNull(imageFile.getOriginalFilename()));
            Files.copy(imageFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            return path.toString(); // Return the image path
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Or handle error as needed
        }
    }
}
