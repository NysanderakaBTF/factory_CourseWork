package com.fox.factory.service;

import com.fox.factory.entities.*;
import com.fox.factory.entities.dto.CatrgoryDto;
import com.fox.factory.entities.dto.CommentCreateDto;
import com.fox.factory.entities.dto.ProductDetailDto;
import com.fox.factory.entities.dto.ProductListDto;
import com.fox.factory.repositories.OrderItemRepository;
import com.fox.factory.repositories.ProductRepository;
import com.fox.factory.security.JwtAuthenticationFilter;
import com.fox.factory.service.mappers.CatrgoryMapper;
import com.fox.factory.service.mappers.CommentMapper;
import com.fox.factory.service.mappers.ProductMapper;

import jakarta.persistence.criteria.Predicate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository repository;
    private OrderItemService orderItemService;
    private ProductMapper mapper;
    private CommentMapper commentMapper;
    private CatrgoryMapper catrgoryMapper;
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private CommentService commentService;

    @Transactional
    public ProductDetailDto create(ProductDetailDto product) {
        return mapper.toProductDetailDto(repository.save(mapper.fromProductDetail(product)));
    }

    public void delete(Long id) {
        var product = repository.findById(id).orElse(null);
        if (product==null)
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        Set<OrderItem> orderItemSet = product.getOrderItemSet();
        for(var i: orderItemSet){
            orderItemService.delete(i.getId());
        }
        repository.delete(product);
    }

    @Transactional
    public ProductDetailDto commentProduct(HttpServletRequest req, Long prooductId, CommentCreateDto comment) {
        User u = jwtAuthenticationFilter.get_user_from_req(req);
        return mapper.toProductDetailDto(repository.findById(prooductId).map(product -> {
            Comment commentObj = commentMapper.toEntityCreate(comment);
            commentObj.setProduct(product);
            commentObj.setAuthor(u);
            commentObj = commentMapper.toEntity(commentService.create(commentMapper.toDtoCreate(commentObj)));
            product.updateRate(comment.getRate());
            product.addComment(commentObj);
            return product;
        }).map(product -> repository.save(product)).orElse(null));
    }

    @Transactional
    public List<ProductListDto> findByCategories(List<CatrgoryDto> cat) {
        return repository.filterProductByCategory(cat.stream()
                        .map(catrgoryMapper::toEntity)
                        .collect(Collectors.toList()))
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<ProductListDto> findByName(String namePart) {
        return repository.filterProductByName(namePart).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public ProductDetailDto update(Long id, ProductDetailDto upd) {
        return mapper.toProductDetailDto(repository.findById(id).map(product ->
        {
            return repository.save(mapper.partialUpdate1(upd, product));
        }).orElse(null));
    }

    @Transactional
    public Product saveFormEntity(Product product) {
        return repository.save(product);
    }

    @Transactional
    public ProductDetailDto findById(Long id) {
        return mapper.toProductDetailDto(repository.findById(id).orElse(null));
    }

    @Transactional
    public List<ProductListDto> findByCategoriesAndNames(List<CatrgoryDto> catrgoryDtos, String name, Pageable pageable) {
        System.out.println(name);
        System.out.println(catrgoryDtos);
        var categories = catrgoryDtos != null ? catrgoryDtos.stream().map(catrgoryMapper::toEntity).toList()
                : null;
        var gotProducts = repository.findAll(
                Specification.where(nameContainsIgnoreCase(name).and((containsCategory(categories)))), pageable
        );
        System.out.println(gotProducts);
        return gotProducts.stream().map(mapper::toDto).toList();
    }

    protected Specification<Product> nameContainsIgnoreCase(String name) {
        if ( name == null || name.isBlank() ) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        var pattern = '%' + name.toUpperCase() + '%';
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.upper(root.get("name")), pattern
                );
    }

    protected Specification<Product> containsCategory(List<Catrgory> catrgories) {
        if(catrgories == null || catrgories.isEmpty() ){
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }
        return (root, query, criteriaBuilder) ->{
            List<Predicate> predicates = new ArrayList<>();
            for (Catrgory cat: catrgories) {
                predicates.add(criteriaBuilder.isMember(cat, root.get("catrgories")));
            }
            return criteriaBuilder.or(predicates.toArray(new Predicate[]{}));
        };
    }

    @Transactional
    public ProductDetailDto addImageToProduct(Long pid, ProductImage image) {
        return repository.findById(pid)
                .map(product -> product.addImage(image))
                .map(o -> repository.save(o))
                .map(mapper::toProductDetailDto)
                .orElse(null);
    }

    @Transactional
    public List<ProductListDto> getAll() {
        return repository.findAll()
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }

}
