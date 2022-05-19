package asia.rtx3090.service;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * @Auther: Bernardo
 * @Date: 2022/5/19 23:54
 * @Description: 创建索引
 */
@Slf4j
@Service
public class CreateIndexService {

    private MongoTemplate mongoTemplate;

    @Autowired
    public CreateIndexService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 设置集合名称
     */
    private static final String COLLECTION_NAME = "users";

    /**
     * 创建升序索引
     *
     * @return 索引名称
     */
    public String createAscendingIndex() {
        // 设置字段名称
        String field = "name";
        // 创建索引
        return mongoTemplate.getCollection(COLLECTION_NAME).createIndex(Indexes.ascending(field));
    }

    /**
     * 创建降序索引
     *
     * @return 索引名称
     */
    public String createDescendingIndex() {
        // 设置字段名称
        String field = "name";
        // 创建索引
        return mongoTemplate.getCollection(COLLECTION_NAME).createIndex(Indexes.descending(field));
    }

    /**
     * 创建升序复合索引
     *
     * @return 索引名称
     */
    public String createCompositeIndex() {
        // 设置字段名称
        String fieldOne = "name";
        String fieldTwo = "age";
        // 创建索引
        return mongoTemplate.getCollection(COLLECTION_NAME).createIndex(Indexes.ascending(fieldOne, fieldTwo));
    }

    /**
     * 创建文字索引
     *
     * @return 索引名称
     */
    public String createTextIndex() {
        // 设置字段名称
        String field = "name";
        // 创建索引
        return mongoTemplate.getCollection(COLLECTION_NAME).createIndex(Indexes.text(field));
    }

    /**
     * 创建哈希索引
     *
     * @return 索引名称
     */
    public String createHashIndex() {
        // 设置字段名称
        String field = "name";
        // 创建索引
        return mongoTemplate.getCollection(COLLECTION_NAME).createIndex(Indexes.hashed(field));
    }

    /**
     * 创建升序唯一索引
     *
     * @return 索引名称
     */
    public String createUniqueIndex() {
        // 设置字段名称
        String field = "name";
        // 配置索引选项为唯一索引
        IndexOptions unique = new IndexOptions().unique(true);
        // 创建索引
        return mongoTemplate.getCollection(COLLECTION_NAME).createIndex(Indexes.ascending(field), unique);
    }

    /**
     * 创建局部索引
     *
     * @return 索引名称
     */
    public String createPartialIndex() {
        // 设置字段名称
        String field = "name";
        // 配置索引选项过滤条件
        IndexOptions partialFilterExpression = new IndexOptions().partialFilterExpression(Filters.exists("name", true));
        // 创建索引
        return mongoTemplate.getCollection(COLLECTION_NAME).createIndex(Indexes.ascending(field), partialFilterExpression);
    }
}
