package asia.rtx3090.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Bernardo
 * @Date: 2022/5/19 22:02
 * @Description: 聚合表达式
 */
@Service
public class AggregateGroupService {

    private MongoTemplate mongoTemplate;

    @Autowired
    public AggregateGroupService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 使用管道操作符 $group 结合 $count 方法进行聚合统计
     *
     * @param collectionName 集合名称
     * @return 聚合结果
     */
    public List<Map> aggregationGroupCount(String collectionName) {
        // 使用管道操作符 $group 进行分组，然后统计各个组的文档数量
        AggregationOperation group = Aggregation.group("age").count().as("numCount");
        // 将操作加入到聚合对象中
        Aggregation aggregation = Aggregation.newAggregation(group);
        // 执行聚合查询
        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, collectionName, Map.class);
        return results.getMappedResults();
    }

    /**
     * 使用管道操作符 $group 结合表达式操作符 $max 进行聚合统计
     *
     * @param collectionName 集合名称
     * @return 聚合结果
     */
    public List<Map> aggregationGroupMax(String collectionName) {
        // 使用管道操作符 $group 进行分组，然后统计各个组文档某字段最大值
        AggregationOperation group = Aggregation.group("sex").max("salary").as("maxSalary");
        // 将操作加入到聚合对象中
        Aggregation aggregation = Aggregation.newAggregation(group);
        // 执行聚合查询
        AggregationResults<Map> mapAggregationResults = mongoTemplate.aggregate(aggregation, collectionName, Map.class);
        return mapAggregationResults.getMappedResults();
    }

    /**
     * 使用管道操作符 $group 结合表达式操作符 $min 进行聚合统计
     *
     * @param collectionName 集合名称
     * @return 聚合结果
     */
    public List<Map> aggregationGroupMin(String collectionName) {
        // 使用管道操作符 $group 进行分组，然后统计各个组文档某字段最小值
        AggregationOperation group = Aggregation.group("sex").min("salary").as("minSalary");
        // 将操作加入到聚合对象中
        Aggregation aggregation = Aggregation.newAggregation(group);
        // 执行聚合查询
        AggregationResults<Map> mapAggregationResults = mongoTemplate.aggregate(aggregation, collectionName, Map.class);
        return mapAggregationResults.getMappedResults();
    }

    /**
     * 使用管道操作符 $group 结合表达式操作符 $sum 进行聚合统计
     *
     * @param collectionName 集合名称
     * @return 聚合结果
     */
    public List<Map> aggregationGroupSum(String collectionName) {
        // 使用管道操作符 $group 进行分组，然后统计各个组文档某字段值合计
        AggregationOperation group = Aggregation.group("sex").sum("salary").as("sumSalary");
        // 将操作加入到聚合对象中
        Aggregation aggregation = Aggregation.newAggregation(group);
        // 执行聚合查询
        AggregationResults<Map> mapAggregationResults = mongoTemplate.aggregate(aggregation, collectionName, Map.class);
        return mapAggregationResults.getMappedResults();
    }

    /**
     * 使用管道操作符 $group 结合表达式操作符 $avg 进行聚合统计
     *
     * @param collectionName 集合名称
     * @return 聚合结果
     */
    public List<Map> aggregationGroupAvg(String collectionName) {
        // 使用管道操作符 $group 进行分组，然后统计各个组文档某字段值平均值
        AggregationOperation group = Aggregation.group("sex").avg("salary").as("avgSalary");
        // 将操作加入到聚合对象中
        Aggregation aggregation = Aggregation.newAggregation(group);
        // 执行聚合查询
        AggregationResults<Map> mapAggregationResults = mongoTemplate.aggregate(aggregation, collectionName, Map.class);
        return mapAggregationResults.getMappedResults();
    }

    /**
     * 使用管道操作符 $group 结合表达式操作符 $last 获取每个组的包含某字段的文档的最后一条数据
     *
     * @param collectionName 集合名称
     * @return 聚合结果
     */
    public List<Map> aggregationGroupFirst(String collectionName) {
        // 先对数据进行排序，然后使用管道操作符 $group 进行分组，最后统计各个组文档某字段值第一个值
        AggregationOperation sort = Aggregation.sort(Sort.by("salary").ascending());
        AggregationOperation group = Aggregation.group("sex").first("salary").as("salaryFirst");
        // 将操作加入到聚合对象中
        Aggregation aggregation = Aggregation.newAggregation(sort,group);
        // 执行聚合查询
        AggregationResults<Map> mapAggregationResults = mongoTemplate.aggregate(aggregation, collectionName, Map.class);
        return mapAggregationResults.getMappedResults();
    }

    /**
     * 使用管道操作符 $group 结合表达式操作符 $push 获取某字段列表
     *
     * @param collectionName 集合名称
     * @return 聚合结果
     */
    public List<Map> aggregationGroupPush(String collectionName) {
        // 先对数据进行排序，然后使用管道操作符 $group 进行分组，然后以数组形式列出某字段的全部值
        AggregationOperation group = Aggregation.group("sex").push("salary").as("salaryFirst");
        // 将操作加入到聚合对象中
        Aggregation aggregation = Aggregation.newAggregation(group);
        // 执行聚合查询
        AggregationResults<Map> mapAggregationResults = mongoTemplate.aggregate(aggregation, collectionName, Map.class);
        return mapAggregationResults.getMappedResults();
    }
}
