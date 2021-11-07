package com.oauth2.google.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oauth2.google.model.category.Category;


/**
 * Repository for {@Link Category}
 * 
 * @author Ines Heni
 *
 */
public interface CategoryRepository extends JpaRepository<Category, Integer>{

}
