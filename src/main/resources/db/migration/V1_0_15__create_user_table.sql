CREATE TABLE IF NOT EXISTS users (
   id BIGINT NOT NULL,
   first_name VARCHAR(50) NOT NULL,
   last_name VARCHAR(100) NOT NULL,
   email VARCHAR(200) NOT NULL,
   password VARCHAR(300) NOT NULL,
   role VARCHAR(50) NOT NULL,
   PRIMARY KEY (id)
);

insert into _user (id, first_name, last_name, email, password, role)
values (1, 'Admin', 'Adminuous', 'admin@mysnemovna.cz', '$2a$10$kV9mcu7qkGQlmmKafc0emetXKlrOCqWbyxRNIPh2ZzIH8nkfiiXUK', 'ADMIN');