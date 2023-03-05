package com.fox.factory.repositories.product_repo_ext;

import com.fox.factory.entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;

public class FindProductByNamePartImpl implements FindProductByNamePart{

    EntityManager entityManager;

    public FindProductByNamePartImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
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
