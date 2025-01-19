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
import java.util.UUID;

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
    public Response addProduct(ProductDto productDto, MultipartFile image) {

        Response response = new Response();
        // Check if category exists
        Optional<Category> category = categoryRepo.findById(productDto.getCatId());
        if (category.isEmpty()) {
             response.setStatus(403);
             response.setMessage("Category is not found.");
             return response;
        }

        // Save image and get path
        String imageUrl = saveImage(image);

        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setCategory(categoryRepo.findById(productDto.getCatId())
                .orElseThrow(() -> new RuntimeException("Category not found")));
        product.setImageUrl(imageUrl); // Set the image URL
        productRepo.save(product);


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

    //Save image and return absolute path
    private String saveImage(MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty()) {
            return null; // Handle as per the requirement
        }

        try {
            // Define the path to save images in the `static/images` directory
            String directory = "src/main/resources/static/images/";
            File folder = new File(directory);
            if (!folder.exists()) {
                folder.mkdirs(); // Create the directory if it doesn't exist
            }

            // Generate a unique filename to prevent conflicts
            String uniqueFileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
            Path path = Path.of(directory, uniqueFileName);

            // Save the image to the directory
            Files.copy(imageFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // Return the URL that points to the image
            return "/images/" + uniqueFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Handle error as needed
        }
    }

}
