-- Insert users
INSERT INTO Users (name, address, birthdate) 
VALUES ('Bharath', 'Bangalore','1999-04-14 15:30:00');

INSERT INTO Users (name, address, birthdate) 
VALUES ('Vamshi', 'Chennai', '1996-11-07 15:30:00');

INSERT INTO Users (name, address, birthdate) 
VALUES ('Shankar', 'Dhone', '1995-08-25 15:30:00');

INSERT INTO Users (name, address, birthdate) 
VALUES ('Sathish', 'Anantapur', '1997-04-03 15:30:00');

-- Insert books with references to userId
INSERT INTO Books (title, subject, author) 
VALUES ('The Great Gatsby', 'Literature', 'F. Scott Fitzgerald');

INSERT INTO Books (title, subject, author) 
VALUES ('1984', 'Dystopian', 'George Orwell');
