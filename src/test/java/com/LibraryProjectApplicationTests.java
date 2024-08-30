package com;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.controllers.UserController;
import com.entities.Users;
import com.repositories.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class LibraryProjectApplicationTests {

	@Autowired
	private UserController usercontroller;	
	
	@MockBean
	private UserRepository userrepo;
	
//	@Test
//	public void getUserstest() {
//		when(userrepo.findAll()).thenReturn(Stream.of(new Users(100, "Bharath", "Bangalore", null, LocalDateTime.now()),
//				new Users(200, "Amrutha", "America", null, LocalDateTime.now())).collect(Collectors.toList()));
//		assertEquals(2, usercontroller.getAll().size());
//		
//	}
	
	
	 @Test
	 public void getAllUsers() {
		 
		 List<Users> mockuser=Arrays.asList(new Users(100, "ABC", "Bethamangala", null, LocalDateTime.now()),
				 new Users(200, "XYZ", "Bangalore", null, LocalDateTime.now()));
		 
		 when(userrepo.findAll()).thenReturn(mockuser);
		 
		 ResponseEntity<List<Users>> response=usercontroller.getAllUsers();
		 
		 assertEquals(HttpStatus.OK, response.getStatusCode());
		 assertEquals(2, response.getBody().size());
				 
		 
	 }
	 
	 @Test
	 public void getOneUser() {
		 Users user=new Users(100, "Bharath", "Bangalore", null, LocalDateTime.now());
		 Users user1=new Users(100, "Bharath", "Bangalore", null, LocalDateTime.now());
		 int userid=1;
		 when(userrepo.findById(userid)).thenReturn(Optional.of(user));
		 
		 ResponseEntity<Users> response=usercontroller.getOneUser(userid);
		 
		 assertEquals(HttpStatus.OK, response.getStatusCode());
		 assertEquals(user, response.getBody());
	 }
	 
	 @Test
	 public void saveUsers() {
		 
		 Users user=new Users(100, "Bharath", "Bangalore", null, LocalDateTime.now());
		 
		 when(userrepo.save(user)).thenReturn(user);
		 
		 ResponseEntity<?> response=usercontroller.saveUsers(user);
		 
		 assertEquals(HttpStatus.OK, response.getStatusCode());
		 assertEquals("User details saved Successfully", response.getBody());
		 
	 }
	 
	 @Test
	 public void deleteUser() {
		 
		 int userId=1;
		 Optional<Users> user=Optional.of(new Users(100, "Bharath", "Bangalore", null, LocalDateTime.now()));
		 when(userrepo.findById(userId)).thenReturn(user);
		 
		 ResponseEntity<?> response=usercontroller.deleteUser(userId);
		 
		 assertEquals(HttpStatus.OK, response.getStatusCode());
		 assertEquals("Successfully Deleted", response.getBody());
		 
	 }

}
