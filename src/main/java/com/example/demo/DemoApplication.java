package com.example.demo;

import java.util.Random;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Entity
@Table(name = "person")
@Data
class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String name;

}

interface PersonRepo extends JpaRepository<Person, Long> {

}

@RestController
@RequestMapping("api/person")
@RequiredArgsConstructor
class PersonResource {

  private final PersonRepo personRepo;

  @GetMapping
  public ResponseEntity getList() {
    return ResponseEntity.ok(personRepo.findAll());
  }

}

@SpringBootApplication
@RequiredArgsConstructor
public class DemoApplication implements CommandLineRunner {

  private final PersonRepo personRepo;

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    Person person = new Person();
    person.setName("person #" + new Random().nextInt());
    personRepo.save(person);
  }
}
