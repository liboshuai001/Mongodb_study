package asia.rtx3090.service;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ViewService {

    private MongoTemplate mongoTemplate;

    @Autowired
    public ViewService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 创建视图
     *
     * @param viewName 视图名称
     * @return 创建视图结果
     */
    public String createView(String collectionName, String viewName) {
        // 定义视图的管道，可以设置显示内容的多个筛选条件
        List<Bson> pipeline = new ArrayList<>();
        if (!mongoTemplate.collectionExists(collectionName)) {
            return "集合还不存在了，还无法创建视图";
        } else if (mongoTemplate.collectionExists(viewName)){
            return "视图已经存在了，不需要进行创建";
        } else {
            // 执行创建视图
            mongoTemplate.getDb().createView(viewName, collectionName, pipeline);
            // 检查新创建的视图是否存在，返回创建结果
            return mongoTemplate.collectionExists(viewName)?"创建视图成功":"创建视图失败";
        }
    }

    /**
     * 删除视图
     * @param viewName 视图名称
     * @return 删除视图的结果
     */
    public String dropView(String viewName) {
        if (mongoTemplate.collectionExists(viewName)) {
            // 执行删除视图的操作
            mongoTemplate.getCollection(viewName).drop();
            // 检查视图删除后是否还存在
            return mongoTemplate.collectionExists(viewName)?"视图删除成功":"视图删除失败";
        } else {
            return "视图不存在，无法进行删除";
        }
    }
}
