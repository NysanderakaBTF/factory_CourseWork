package com.fox.factory.repositories;

import com.fox.factory.entities.Product;
import com.fox.factory.repositories.product_repo_ext.FindProductByCategories;
import com.fox.factory.repositories.product_repo_ext.FindProductByNamePart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> , FindProductByNamePart, FindProductByCategories {
}