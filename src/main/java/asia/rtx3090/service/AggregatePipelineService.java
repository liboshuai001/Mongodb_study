package asia.rtx3090.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Bernardo
 * @Date: 2022/5/19 23:00
 * @Description: 聚合管道操作符
 */
@Slf4j
@Service
public class AggregatePipelineService {

    private MongoTemplate mongoTemplate;

    @Autowired
    public AggregatePipelineService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 设置集合名称
     */
    private static final String COLLECTION_NAME = "users";

    /**
     * 使用 $group 和 $match 聚合,先使用 $match 过滤文档，然后再使用 $group 进行分组
     *
     * @return 聚合结果
     */
    public List<Map> aggregateGroupMatch() {
        // 设置聚合条件，先使用 $match 过滤岁数大于 25 的用户，然后按性别分组，统计每组用户工资最高值
        AggregationOperation match = Aggregation.match(Criteria.where("age").lt(35));
        AggregationOperation group = Aggregation.group("sex").max("salary").as("sexSalary");
        // 将操作加入到聚合对象中
        Aggregation aggregation = Aggregation.newAggregation(match, group);
        // 执行聚合操作
        AggregationResults<Map> mapAggregationResults = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, Map.class);
        for (Map result : mapAggregationResults.getMappedResults()) {
            log.info("{}", result);
        }
        return mapAggregationResults.getMappedResults();
    }

    /**
     * 使用 $group 和 $sort 聚合,先使用 $group 进行分组，然后再使用 $sort 排序
     *
     * @return 聚合结果
     */
    public List<Map> aggregateGroupSort() {
        // 设置聚合条件，按岁数分组，然后统计每组用户工资最大值和用户数，按每组用户工资最大值升序排序
        AggregationOperation group = Aggregation.group("age")
                .max("salary").as("ageSalary")
                .count().as("ageCount");
        AggregationOperation sort = Aggregation.sort(Sort.by("ageSalary").ascending());
        // 将操作加入到聚合对象中
        Aggregation aggregation = Aggregation.newAggregation(group, sort);
        // 执行聚合查询
        AggregationResults<Map> mapAggregationResults = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, Map.class);
        for (Map result : mapAggregationResults.getMappedResults()) {
            log.info("{}", result);
        }
        return mapAggregationResults.getMappedResults();
    }

    /**
     * 使用 $group 和 $limit 聚合,先使用 $group 进行分组，然后再使用 $limit 限制一定数目文档
     *
     * @return 聚合结果
     */
    public List<Map> aggregateGroupLimit() {
        // 设置聚合条件，先按岁数分组，然后求每组用户的工资总数、最大值、最小值、平均值，限制只能显示五条
        AggregationOperation group = Aggregation.group("age")
                .sum("salary").as("sumSalary")
                .max("salary").as("maxSalary")
                .min("salary").as("minSalary")
                .avg("salary").as("avgSalary");
        AggregationOperation limit = Aggregation.limit(5L);
        // 将操作加入到聚合对象中
        Aggregation aggregation = Aggregation.newAggregation(group, limit);
        // 执行聚合查询
        AggregationResults<Map> mapAggregationResults = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, Map.class);
        for (Map result : mapAggregationResults.getMappedResults()) {
            log.info("{}", result);
        }
        return mapAggregationResults.getMappedResults();
    }

    /**
     * 使用 $group 和 $skip 聚合,先使用 $group 进行分组，然后再使用 $skip 跳过一定数目文档
     *
     * @return 聚合结果
     */
    public List<Map> aggregateGroupSkip() {
        // 设置聚合条件，先按岁数分组，然后求每组用户的工资总数、最大值、最小值、平均值，跳过前 2 条
        AggregationOperation group = Aggregation.group("age")
                .sum("salary").as("sumSalary")
                .max("salary").as("maxSalary")
                .min("salary").as("minSalary")
                .avg("salary").as("avgSalary");
        AggregationOperation skip = Aggregation.skip(2L);
        // 将操作加入到聚合对象中
        Aggregation aggregation = Aggregation.newAggregation(group, skip);
        // 执行聚合查询
        AggregationResults<Map> mapAggregationResults = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, Map.class);
        for (Map result : mapAggregationResults.getMappedResults()) {
            log.info("{}", result);
        }
        return mapAggregationResults.getMappedResults();
    }

    /**
     * 使用 $group 和 $project 聚合,先使用 $group 进行分组，然后再使用 $project 限制显示的字段
     *
     * @return 聚合结果
     */
    public List<Map> aggregateGroupProject() {
        // 设置聚合条件,按岁数分组，然后求每组用户工资最大值、最小值，然后使用 $project 限制值显示 salaryMax 字段
        AggregationOperation group = Aggregation.group("age")
                .max("salary").as("maxSalary")
                .min("salary").as("minSalary");
        AggregationOperation project = Aggregation.project("maxSalary");
        // 将操作加入到聚合对象中
        Aggregation aggregation = Aggregation.newAggregation(group, project);
        // 执行聚合查询
        AggregationResults<Map> mapAggregationResults = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, Map.class);
        for (Map result : mapAggregationResults.getMappedResults()) {
            log.info("{}", result);
        }
        return mapAggregationResults.getMappedResults();
    }

    /**
     * 使用 $group 和 $unwind 聚合,先使用 $project 进行分组，然后再使用 $unwind 拆分文档中的数组为一条新文档记录
     *
     * @return 聚合结果
     */
    public List<Map> aggregateProjectUnwind() {
        // 设置聚合条件，设置显示`name`、`age`、`title`字段，然后将结果中的多条文档按 title 字段进行拆分
        AggregationOperation project = Aggregation.project("name", "age", "title");
        AggregationOperation unwind = Aggregation.unwind("title");
        // 将操作加入到聚合对象中
        Aggregation aggregation = Aggregation.newAggregation(project, unwind);
        // 执行聚合查询
        AggregationResults<Map> mapAggregationResults = mongoTemplate.aggregate(aggregation, COLLECTION_NAME, Map.class);
        for (Map result : mapAggregationResults.getMappedResults()) {
            log.info("{}", result);
        }
        return mapAggregationResults.getMappedResults();
    }
}
