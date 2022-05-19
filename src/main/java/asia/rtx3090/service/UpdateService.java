package asia.rtx3090.service;

import asia.rtx3090.entity.CostmerEntity;
import asia.rtx3090.vo.CostmerVO;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.ExecutableUpdateOperation;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class UpdateService {

    private MongoTemplate mongoTemplate;

    @Autowired
    public UpdateService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 更新集合中【匹配】查询到的第一条文档数据，如果没有找到就【创建并插入一个新文档】
     *
     * @param collectionName 集合名称
     * @return 执行更新的结果
     */
    public String update(String collectionName) {
        // 设置条件对象
        Criteria isSexCriteria = Criteria.where("sex").is("woman");
        // 创建查询对象，然后将条件对象添加到其中
        Query query = new Query(isSexCriteria);
        // 创建更新对象,并设置更新的内容
        Update update = new Update().set("age", 13).set("name", "小燕");
        // 执行更新，如果没有找到匹配查询的文档，则创建并插入一个新文档
        UpdateResult upsertResult = mongoTemplate.upsert(query, update, CostmerEntity.class, collectionName);
        // 输出结果信息
        return "匹配到" + upsertResult.getMatchedCount() + "条数据,对第一条数据进行了更改";
    }

    /**
     * 更新集合中【匹配】查询到的【文档数据集合】中的【第一条数据】
     *
     * @param collectionName 集合名称
     * @return 执行更新的结果
     */
    public String updateFirst(String collectionName) {
        // 设置条件对象
        Criteria isNameCriteria = Criteria.where("name").is("Bernardo");
        // 设置查询对象，然后将条件对象添加到其中，并设置排序
        Query query = new Query(isNameCriteria).with(Sort.by("age").ascending());
        // 创建更新对象,并设置更新的内容
        Update update = new Update().set("sex", "man").set("address", "ShangHai");
        // 执行更新
        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, CostmerEntity.class, collectionName);
        // 输出结果信息
        return "共匹配到" + updateResult.getMatchedCount() + "条数据,修改了" + updateResult.getModifiedCount() + "条数据";
    }

    /**
     * 更新【匹配查询】到的【文档数据集合】中的【所有数据】
     *
     * @param collectionName 集合名称
     * @return 执行更新的结果
     */
    public String updateMany(String collectionName) {
        // 设置条件对象
        Criteria ltAgeCriteria = Criteria.where("age").lt(18);
        // 设置查询对象
        Query query = new Query(ltAgeCriteria);
        // 设置更新字段和更新的内容
        Update update = new Update().set("address", "BeiJing").set("sex", "woman");
        // 执行更新
        UpdateResult updateResult = mongoTemplate.updateMulti(query, update, CostmerEntity.class, collectionName);
        // 输出结果信息
        return  "总共匹配到" + updateResult.getMatchedCount() + "条数据,修改了" + updateResult.getModifiedCount() + "条数据";
    }

}