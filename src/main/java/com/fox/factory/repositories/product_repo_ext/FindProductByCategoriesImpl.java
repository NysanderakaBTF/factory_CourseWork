package com.fox.factory.repositories.product_repo_ext;

import com.fox.factory.entities.Catrgory;
import com.fox.factory.entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;

import java.util.ArrayList;
import java.util.List;


/**
 * This class is a repository that implements the FindProductByCategories interface and uses the
 * entityManager to query the database for products that have a category that is in the list of
 * categories passed to the filterProductByCategory method.
 */
public class FindProductByCategoriesImpl implements FindProductByCategories{
    EntityManager entityManager;

// A constructor that takes an EntityManager as a parameter and sets the entityManager field to the
// parameter.
    public FindProductByCategoriesImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
/**
 * I want to get all the products that have at least one of the categories in the list.
 * I'm using Spring Boot and Hibernate. 
 * @param list List of categories
 * @return A list of products that are in the list of categories.
 */
    @Override
    public List<Product> filterProductByCategory(List<Catrgory> list) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);

        if(list.size()==0){
            query.select(root);
            TypedQuery<Product> res = entityManager.createQuery(query);
            return res.getResultList();
        }
        query.select(root).where(root.get("catrgories").in(list));
        TypedQuery<Product> res = entityManager.createQuery(query);
        return res.getResultList();
    }
}
