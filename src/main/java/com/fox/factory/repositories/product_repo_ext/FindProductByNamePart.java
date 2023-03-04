package com.fox.factory.repositories.product_repo_ext;

import com.fox.factory.entities.Catrgory;
import com.fox.factory.entities.Product;

import java.util.List;

public interface FindProductByNamePart {
    List<Product> filterProductByCategory(String namePart);
}
