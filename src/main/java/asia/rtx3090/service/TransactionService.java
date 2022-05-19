package asia.rtx3090.service;

import asia.rtx3090.entity.CostmerEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

/**
 * @Auther: Bernardo
 * @Date: 2022/5/20 00:42
 * @Description: 事务测试服务
 */
@Slf4j
@Service
public class TransactionService {

    private MongoTemplate mongoTemplate;

    @Autowired
    public TransactionService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 设置集合名称
     */
    private static final String COLLECTION_NAME = "users";

    @Transactional(rollbackFor = Exception.class)
    public CostmerEntity transactionTest() {
        // 设置两个用户信息
        CostmerEntity costmerEntity = CostmerEntity.builder()
                .id("20")
                .name("Ai")
                .age(22)
                .hobbies(Arrays.asList("books", "games"))
                .build();
        // 插入数据
        CostmerEntity insert = mongoTemplate.insert(costmerEntity, COLLECTION_NAME);
        // 人为制造异常，查看数据是否回滚
        int error = 1 / 0;
        return insert;
    }

}
