package ru.coursemodel;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { CoursemodelApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class CoursemodelApplicationTests {

}
