package asia.rtx3090.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QueryCollectionService {

    private MongoTemplate mongoTemplate;

    @Autowired
    public QueryCollectionService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 获取所有集合名称
     *
     * @return 集合名称列表
     */
    public List<String> getCollectionNames() {
        // 执行获取集合名称列表
        return new ArrayList<>(mongoTemplate.getCollectionNames());
    }

    /**
     * 检查指定集合名称是否存在
     *
     * @param collectionName 集合名称
     * @return 检查结果
     */
    public String checkCollectionExists(String collectionName) {
        String successfulResult = "集合【" + collectionName + "】已经存在";
        String failureResult = "集合【" + collectionName + "】还不存在";
        return mongoTemplate.collectionExists(collectionName) ? successfulResult : failureResult;
    }
}
