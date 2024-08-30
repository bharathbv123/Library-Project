package com.controllers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entities.Books;
import com.exceptions.DuplicateExceptions;
import com.exceptions.NotFoundExceptions;
import com.repositories.BooksRepository;


@RestController
@RequestMapping("Books")
public class BookController {
	
	Logger logger=LoggerFactory.getLogger(BookController.class);
	
	@Autowired
	BooksRepository booksrepo;
	
	@GetMapping("")
	public ResponseEntity<List<Books>> getallBooks(){
		List<Books> list=booksrepo.findAll();
		if(list.size()==0) {
			throw new NotFoundExceptions("404", "Books not Found");
		}else {
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getOneBook(@PathVariable int id) {
		Optional<Books> opt=booksrepo.findById(id);
		if(!opt.isPresent()) {
			throw new NotFoundExceptions("404", "Books details with Id "+id+" not found");
		}else {
			return new ResponseEntity<>(opt.get(),HttpStatus.OK);
		}
	}
	
	@PostMapping("/saveBook")
	public ResponseEntity<?> saveBook(@RequestBody Books book){
		if(booksrepo.existsById(book.getBookId())) {
			throw new DuplicateExceptions("400", "Book with Id +"+book.getBookId()+" already exist");
		}else {
			booksrepo.save(book);
			return new ResponseEntity<>("Books details saved successfully",HttpStatus.CREATED);
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateBook(@RequestBody Books book){
		if(booksrepo.existsById(book.getBookId())) {
			booksrepo.save(book);
			return new ResponseEntity<>("Updated successfully",HttpStatus.OK);
		}else {
			throw new NotFoundExceptions("404", "Book with Id "+book.getBookId()+" Not Found");
		}
	}
	
	@GetMapping("/search")
	public List<Books> getSubjects(@RequestParam String prefix){
		List<Books> list=booksrepo.findByTitleStartingWith(prefix);
		return list;
	}
	
	@GetMapping("/subject")
	public List<String> unassignedBook() {
		
		List<Books> book=booksrepo.findAll();
		List<String> list=book.stream().map(Books::getSubject).distinct().collect(Collectors.toList());
		return list;
		
	}
}
