package com.example.obejercicioSesion11y12.controller;

import com.example.obejercicioSesion11y12.entitites.Laptop;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LaptopControllerTest {

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        restTemplateBuilder = new RestTemplateBuilder().rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @AfterEach
    void tearDown() {
        testRestTemplate.delete("/laptops");
    }

    @Test
    void findAll() {
        createLaptop(new Laptop(null, "HP", 1000.0, "Windows", true));
        createLaptop(new Laptop(null, "Apple", 20000.0, "MacOS", false));

        ResponseEntity<Laptop[]> response =
                testRestTemplate.getForEntity("/laptops", Laptop[].class);
        Arrays.stream(response.getBody()).forEach(laptop -> System.out.println(laptop.toString()));
        assertEquals(2, response.getBody().length);
    }

    @Test
    void findOneById() {
        Long idLaptopCreated = createLaptop(new Laptop(null, "Intel", 1500.0, "Windows", false)).getBody().getId();
        ResponseEntity<Laptop> response =
                testRestTemplate.getForEntity("/laptops/" + idLaptopCreated, Laptop.class);

        Laptop laptop = response.getBody();
        assertEquals("Intel", laptop.getModel());
        assertEquals("Windows", laptop.getOperatingSystem());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void create() {
        ResponseEntity<Laptop> response =
                testRestTemplate.postForEntity("/laptops",new Laptop(null, "Lenovo", 5000.0, "Linux", false), Laptop.class);
        Laptop laptop = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Lenovo", laptop.getModel());
        assertEquals(5000.0, laptop.getPrice());
    }

    @Test
    void update() {
        Laptop laptopCreated = createLaptop(new Laptop(null, "Lenovo", 5000.0, "Linux", false)).getBody();

        testRestTemplate.put("/laptops",new Laptop(laptopCreated.getId(), "EXO", 5000.0, "Windows", true));
        Laptop laptopModified = getLaptop(laptopCreated.getId()).getBody();

        assertEquals("EXO", laptopModified.getModel());
        assertEquals("Windows", laptopModified.getOperatingSystem());
        assertEquals(true, laptopModified.getGamer());
    }

    @Test
    void delete() {
        Laptop laptopCreated = createLaptop(new Laptop(null, "Lenovo", 800.0, "Linux", true)).getBody();

        testRestTemplate.delete("/laptops/"+ laptopCreated.getId());
        ResponseEntity<Laptop> response = getLaptop(laptopCreated.getId());

        assertTrue(response.getStatusCode() == HttpStatus.NOT_FOUND);

    }

    @Test
    void deleteAll() {
        createLaptop(new Laptop(null, "HP", 1000.0, "Windows", true));
        createLaptop(new Laptop(null, "Apple", 24000.0, "MacOS", false));
        createLaptop(new Laptop(null, "Samsung", 1200.0, "Linux", false));
        createLaptop(new Laptop(null, "BGH", 20500.0, "Windows", false));

        testRestTemplate.delete("/laptops");

        assertEquals(null, getAllLaptops().getBody());
    }

    ResponseEntity<Laptop> createLaptop(Laptop laptop){

        return testRestTemplate.postForEntity("/laptops",laptop, Laptop.class);
    }

    ResponseEntity<Laptop> getLaptop(Long id){

        return testRestTemplate.getForEntity("/laptops/" + id, Laptop.class);
    }

    ResponseEntity<Laptop[]> getAllLaptops(){

        return testRestTemplate.getForEntity("/laptops", Laptop[].class);
    }
}