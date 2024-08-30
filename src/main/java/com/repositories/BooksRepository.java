package com.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entities.Books;

@Repository
public interface BooksRepository extends JpaRepository<Books, Integer>{
	
	List<Books> findByTitleStartingWith(String prefix);
	
	
//	findByTitleStartsWith
}
