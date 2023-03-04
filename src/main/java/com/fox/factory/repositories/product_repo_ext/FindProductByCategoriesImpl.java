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


public class FindProductByCategoriesImpl implements FindProductByCategories{
    EntityManager entityManager;

    public FindProductByCategoriesImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
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
