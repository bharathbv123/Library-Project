package com.repositories;

import java.time.LocalDateTime;
//import java.awt.print.Book;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.entities.Books;
import com.entities.Users;


@Repository
public interface UserRepository extends JpaRepository<Users, Integer>{
	
	boolean existsByName(String name);
	
	List<Users> findByIssuedBookIsNotNull();
	
	@Query("SELECT COUNT(b) FROM Users u JOIN u.issuedBook b WHERE b.bookId = :bookId")
	long getBookIssuedCount(@Param("bookId") int bookId);

	List<Users> findByIssuedBookIsNull();

//	List<Users> findByBirthdateFromDateAndTo Date(LocalDateTime startdate, LocalDateTime enddate);

	@Query("SELECT u FROM Users u WHERE u.birthdate BETWEEN :startDate AND :endDate")
    List<Users> findUsersByBirthdateBetween(LocalDateTime startDate, LocalDateTime endDate);
	
	
}
