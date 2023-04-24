package com.fox.factory.repositories.product_repo_ext;

import com.fox.factory.entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

/**
 * This class is a service that implements the FindProductByNamePart interface and uses the
 * EntityManager to query the database for products that have a name that contains the namePart
 * parameter.
 */
public class FindProductByNamePartImpl implements FindProductByNamePart{

    EntityManager entityManager;

// A constructor that takes an EntityManager as a parameter and sets the entityManager field to the
// parameter.
    public FindProductByNamePartImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
/**
 * Return a list of products whose name contains the given string.
 * 
 * @param namePart the part of the name that you want to search for
 * @return A list of products that match the namePart.
 */
    @Override
    public List<Product> filterProductByName(String namePart) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Root<Product> root = query.from(Product.class);
        query.select(root).where(builder.like(root.get("name"), "%"+namePart+"%"));
        TypedQuery<Product> res = entityManager.createQuery(query);
        return res.getResultList();
    }
}
