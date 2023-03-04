package com.fox.factory.repositories.product_repo_ext;

import com.fox.factory.entities.Catrgory;
import com.fox.factory.entities.Product;

import java.util.List;

public interface FindProductByCategories {
    List<Product> filterProductByCategory(List<Catrgory> list);

}
