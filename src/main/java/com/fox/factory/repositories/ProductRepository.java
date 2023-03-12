package com.fox.factory.repositories;

import com.fox.factory.entities.Product;
import com.fox.factory.repositories.product_repo_ext.FindProductByCategories;
import com.fox.factory.repositories.product_repo_ext.FindProductByNamePart;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, Long> , FindProductByNamePart, FindProductByCategories, JpaSpecificationExecutor<Product> {
}