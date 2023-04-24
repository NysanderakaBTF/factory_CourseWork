package com.fox.factory.repositories.product_repo_ext;

import com.fox.factory.entities.Catrgory;
import com.fox.factory.entities.Product;

import java.util.List;

// An interface.
public interface FindProductByNamePart {
    List<Product> filterProductByName(String namePart);
}
