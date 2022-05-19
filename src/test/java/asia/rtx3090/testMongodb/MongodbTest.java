package asia.rtx3090.testMongodb;

import asia.rtx3090.CostmerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CostmerApplication.class)
public class MongodbTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testFind() {

    }
}
