package asia.rtx3090.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.validation.Validator;
import org.springframework.stereotype.Service;

@Service
public class CreateCollectionService {

    private MongoTemplate mongoTemplate;

    @Autowired
    public CreateCollectionService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 创建【集合】
     * 创建一个没有大小限制的集合（默认集合创建方式）
     *
     * @param collectionName 集合名称
     * @return 集合创建结果
     */
    public String createCollection(String collectionName) {
        // 创建集合并返回集合信息
        mongoTemplate.createCollection(collectionName);
        // 检测新创建的集合是否存在，返回创建结果
        return mongoTemplate.collectionExists(collectionName)?"创建集合成功":"创建集合失败";
    }

    /**
     * 创建固定大小集合
     * 创建集合并设置“capped=true”，创建固定大小集合。可以配置参数size限定文档大小，配置maxDocuments限定文档数量
     *
     * @param collectionName 集合名称
     * @return 集合创建结果
     */
    public String createCollectionFixedSize(String collectionName) {
        // 设置集合参数
        long size = 1024L;
        long max = 5L;
        // 创建固定大小集合
        CollectionOptions collectionOptions = CollectionOptions.empty()
                // 创建固定大小集合，固定大小集合指有着固定大小的集合，当达到最大值时，他会自动覆盖最早的集合
                .capped()
                // 固定集合指定一个最大值，以千字节计（kb）。如果capped()为true，也需要指定此字段
                .size(size)
                // 指定固定集合中包含文档的最大数量
                .maxDocuments(max);
        // 执行创建集合
        mongoTemplate.createCollection(collectionName, collectionOptions);
        // 检测新创建的集合是否存在，返回创建结果
        return mongoTemplate.collectionExists(collectionName)?"创建固定大小集合成功":"创建固定大小集合失败";
    }


    /**
     * 创建【验证文档数据】的集合
     *
     * 创建集合并在文档"插入"与"更新"时进行数据效验，如果符合创建集合设置的条件就进允许更新与插入，否则则按照设置的设置的策略进行处理。
     *
     * * 效验级别：
     *   - off：关闭数据校验。
     *   - strict：(默认值) 对所有的文档"插入"与"更新"操作有效。
     *   - moderate：仅对"插入"和满足校验规则的"文档"做"更新"操作有效。对已存在的不符合校验规则的"文档"无效。
     * * 执行策略：
     *   - error：(默认值) 文档必须满足校验规则，才能被写入。
     *   - warn：对于"文档"不符合校验规则的 MongoDB 允许写入，但会记录一条告警到 mongod.log 中去。日志内容记录报错信息以及该"文档"的完整记录。
     *
     * @return 创建集合结果
     */
    public String createCollectionValidation(String collectionName) {
        // 设置验证条件，只允许年龄大于等于30的用户信息插入
        Criteria ageCriteria = Criteria.where("age").gte(30);
        // 创建带有验证条件的集合
        CollectionOptions collectionOptions = CollectionOptions.empty()
                // 设置验证条件
                .validator(Validator.criteria(ageCriteria))
                // 设置效验级别为strict(严格)
                .strictValidation()
                // 设置效验不通过后执行的动作Error报错
                .failOnValidationError();
        // 执行创建集合
        mongoTemplate.createCollection(collectionName,collectionOptions);
        // 检测新创建的集合是否存在，返回创建结果
        return mongoTemplate.collectionExists(collectionName)?"创建带验证条件的集合成功":"创建带验证条件的集合失败";
    }
}