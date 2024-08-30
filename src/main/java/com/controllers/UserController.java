package com.controllers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entities.Books;
import com.entities.Users;
import com.exceptions.BookIssueException;
import com.exceptions.DuplicateExceptions;
import com.exceptions.InvalidReturnException;
import com.exceptions.NotFoundExceptions;
import com.repositories.BooksRepository;
import com.repositories.UserRepository;

@RestController
@RequestMapping("/users")
public class UserController {
	
	Logger logger=LoggerFactory.getLogger(UserController.class);
	
	
	@Autowired
	UserRepository userrepo;
	
	@Autowired
	BooksRepository bookrepo;
	
	@GetMapping("")
	public ResponseEntity<List<Users>> getAllUsers(){
		List<Users> list=userrepo.findAll();
		if(list.size()==0) {
			throw new NotFoundExceptions("404", "User details Not Found");
		}
		logger.info("Fetching All details");
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
//	@GetMapping("")
//	public List<Users> getAll(){
//		List<Users> list=userrepo.findAll();
//		System.out.println("Getting data from db: "+list);
//		return list;
//	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Users> getOneUser(@PathVariable int id){
		Optional<Users> opt=userrepo.findById(id);
		if(!opt.isPresent()) {
			throw new NotFoundExceptions("404", "User details with Id "+id+" Not Found");
		}else {
			logger.info("Fetching One User details");
			return new ResponseEntity<>(opt.get(),HttpStatus.OK);
		}
	}
	
	@PostMapping("/newuser")
	public ResponseEntity<?> saveUsers(@RequestBody Users user){
		if(userrepo.existsByName(user.getName())) {
			throw new DuplicateExceptions("400","user with name "+user.getName()+" already exists");
		}
		userrepo.save(user);
		logger.info("Saving User details");
		return new ResponseEntity<>("User details saved Successfully",HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateUsers(@RequestBody Users user){
		if(userrepo.existsById(user.getUserId())) {
			userrepo.save(user);
			logger.info("Updating User details");
			return new ResponseEntity<>("Updated successfully", HttpStatus.ACCEPTED);
		}else {
			throw new NotFoundExceptions("404", "User with name "+user.getUserId()+" Not found");
		}

	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable int id){
		Optional<Users> opt=userrepo.findById(id);
		if(opt.isPresent()) {
			logger.info("Deleting User details");
			userrepo.deleteById(id);
			return new ResponseEntity<>("Successfully Deleted",HttpStatus.OK);
		}else {
			throw new NotFoundExceptions("404", "User with Id "+id+" not found");
		}
		
	}
	
//	@PutMapping("/issueBook/{userid}/{bookId}")
//	public ResponseEntity<?> issueBookToUser(@PathVariable int userid, @PathVariable int bookId){
//		
//		Optional<Users> opt=userrepo.findById(userid);
//		if(!opt.isPresent()) {
//			throw new NotFoundExceptions("404", "Book with Id "+bookId+" not Found");
//		}
//		Users user=opt.get();
////		if(!(user.getIssuedBook().size()>2)) {
////			throw new BookIssueException("401", "Member have already 2 Books, more than 2 books cannot provide");
////		}
//		if(userrepo.getBookIssuedCount(bookId) != 0) {
//			throw new BookIssueException("Issue_err", "book with id " +bookId+ "is already under issue to the member");
//	}
//		Optional<Books> optb=bookrepo.findById(bookId);
//		if(!optb.isPresent()) {
//			throw new NotFoundExceptions("404", "Book with Id "+bookId+" not found");
//		}
//		Books book=optb.get();
//		//m.setIssuedBook(new Book(bookId));
//		user.setIssuedBook((List<Books>) new Books(bookId));
//		userrepo.save(user);
//		return new ResponseEntity<>("Book with Id issued to member with",HttpStatus.CREATED);
//		
//	}
	
	@PutMapping("/issueBook/{userId}/{bookId}")
	public ResponseEntity<?> issueBook(@PathVariable int userId, @PathVariable int bookId){
		Optional<Users> opt=userrepo.findById(userId);
		if(!opt.isPresent()) {
			throw new NotFoundExceptions("404", "User not Found");
		}
		Users user=opt.get();
		
		Optional<Books> optbk=bookrepo.findById(bookId);
		if(!optbk.isPresent()) {
			throw new NotFoundExceptions("404", "Book not Found");
		}
		
		Books book=optbk.get();
		if(book.getUser()!=null) {
			throw new BookIssueException("401", "Book Already issued to another user");
		}
		
		book.setUser(user);
		user.getIssuedBook().add(book);
		bookrepo.save(book);
		userrepo.save(user);
		logger.info("Successfully assigned Book");
		return new ResponseEntity<>("Book assigned successfully",HttpStatus.OK);
		
	}

	@PutMapping("/returnBook/{userId}/{bookId}")
	public ResponseEntity<?> returnBook(@PathVariable int userId, @PathVariable int bookId){
		
		Optional<Users> opt=userrepo.findById(userId);
		
		if(!opt.isPresent()) {
			throw new NotFoundExceptions("404", "User not Found");
		}
		Users user=opt.get();
		
		Optional<Books> optbk=bookrepo.findById(bookId);
		
		if(!optbk.isPresent()) {
			throw new NotFoundExceptions("404", "User not Found");
		}
		
		Books book=optbk.get();
		
		if(book.getUser()==null) {
			throw new InvalidReturnException("401", "User don't have any book to return");
		}
		
		if(user.getIssuedBook()==null) {
			throw new InvalidReturnException("401", "User don't have any book to return");
		}
		
		user.setIssuedBook(null);
		book.setUser(null);
		userrepo.save(user);
		bookrepo.save(book);
		logger.info("Book Returned Successfully");
		return new ResponseEntity<>("Book Returned Successfully",HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/IssuedBook")
	public List<Users> getUsers(){
		List<Users> list=userrepo.findByIssuedBookIsNotNull();
		return list;
	}
	
	@GetMapping("/UnassigendUser")
	public List<Users> getUnassignedUsers(){
		List<Users> list=userrepo.findByIssuedBookIsNull();
		return list;
	}
	
	@GetMapping("/birthdate")
	public List<Users> findBetween(@RequestParam String startDate, @RequestParam String endDate){
		
		LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
		List<Users> list=userrepo.findUsersByBirthdateBetween(start,end);
		logger.info("Fetchin details between given dates");
		return list;
		
	}
	
}
