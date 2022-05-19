package asia.rtx3090.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class RemoveCollectionService {

    private MongoTemplate mongoTemplate;

    @Autowired
    public RemoveCollectionService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 删除指定名称的集合
     *
     * @return 删除集合结果
     */
    public String dropCollection(String collectionName) {
        // 执行删除集合
        mongoTemplate.getCollection(collectionName).drop();
        // 查询删除后的集合是否存在
        return mongoTemplate.collectionExists(collectionName)?"集合删除失败":"集合删除成功";
    }
}
