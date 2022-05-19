package asia.rtx3090.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * @Auther: Bernardo
 * @Date: 2022/5/20 00:32
 * @Description: 删除索引
 */
@Slf4j
@Service
public class RemoveIndexService {

    private MongoTemplate mongoTemplate;

    @Autowired
    public RemoveIndexService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 设置集合名称
     **/
    private static final String COLLECTION_NAME = "users";

    /**
     * 根据索引名称移除索引
     */
    public void removeIndex() {
        // 设置索引名称
        String indexName = "name_1";
        // 删除集合中的指定索引
        mongoTemplate.getCollection(COLLECTION_NAME).dropIndex(indexName);
    }

    /**
     * 移除全部索引
     */
    public void removeAllIndex() {
        // 删除集合中的所有索引
        mongoTemplate.getCollection(COLLECTION_NAME).dropIndexes();
    }
}
