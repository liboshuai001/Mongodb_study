package asia.rtx3090.service;

import com.mongodb.client.ListIndexesIterable;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Bernardo
 * @Date: 2022/5/20 00:12
 * @Description: 查询索引
 */
@Slf4j
@Service
public class QueryIndexService {

    private MongoTemplate mongoTemplate;

    @Autowired
    public QueryIndexService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /** 设置集合名称 */
    private static final String COLLECTION_NAME = "users";

    /**
     * 获取当前【集合】对应的【所有索引】的【名称列表】
     *
     * @return 当前【集合】所有【索引名称列表】
     */
    public List<Document> getIndexAll() {
        // 获取集合中所有索引
        ListIndexesIterable<Document> documentListIndexesIterable = mongoTemplate.getCollection(COLLECTION_NAME).listIndexes();
        // 获取集合中全部索引信息
        List<Document> list = new ArrayList<>();
        for (Document document : documentListIndexesIterable) {
            log.info("索引列表：{}",document);
            list.add(document);
        }
        return list;
    }
}
