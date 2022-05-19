package asia.rtx3090.service;

import asia.rtx3090.entity.CostmerEntity;
import asia.rtx3090.vo.CostmerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueryService {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public QueryService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 查询集合中的【全部】文档数据
     *
     * @param collectionName 集合名称
     * @return 查询到的文档数据
     */
    public List<CostmerVO> findAll(String collectionName) {
        // 执行查询集合中全部文档信息
        List<CostmerEntity> costmerEntities = mongoTemplate.findAll(CostmerEntity.class, collectionName);
        // 进行数据转化
        return costmerEntities.stream()
                .map(costmerEntity -> CostmerVO.builder()
                        .id(costmerEntity.getId())
                        .name(costmerEntity.getName())
                        .age(costmerEntity.getAge())
                        .hobbies(costmerEntity.getHobbies())
                        .build()).collect(Collectors.toList());
    }

    /**
     * 根据【文档ID】查询集合中文档数据
     *
     * @param collectionName 集合名称
     * @param id             文档id
     * @return 查询到的文档数据
     */
    public CostmerVO findById(String collectionName, String id) {
        // 根据文档ID查询集合中文档数据，并转换为对应 Java 对象
        CostmerEntity costmerEntity = mongoTemplate.findById(id, CostmerEntity.class, collectionName);
        // 进行数据转化
        return CostmerVO.builder()
                .id(costmerEntity.getId())
                .name(costmerEntity.getName())
                .age(costmerEntity.getAge())
                .hobbies(costmerEntity.getHobbies())
                .build();
    }

    /**
     * 根据【条件】查询集合中【符合条件】的文档，只取【第一条】数据
     *
     * @param collectionName 集合名称
     * @param age            年龄
     * @return 查询到的文档数据
     */
    public CostmerVO findOne(String collectionName, Integer age) {
        // 创建条件对象
        Criteria isAgeCriteria = Criteria.where("age").is(age);
        // 创建查询对象，然后将条件对象添加到其中
        Query query = new Query(isAgeCriteria);
        // 查询一条文档，如果查询结果中有多条文档，那么就取第一条
        CostmerEntity costmerEntity = mongoTemplate.findOne(query, CostmerEntity.class, collectionName);
        // 数据转化
        return CostmerVO.builder()
                .id(costmerEntity.getId())
                .name(costmerEntity.getName())
                .age(costmerEntity.getAge())
                .hobbies(costmerEntity.getHobbies())
                .build();
    }

    /**
     * 根据【条件】查询集合中【符合条件】的文档，获取其【文档列表】
     *
     * @param collectionName 集合名称
     * @param age            年龄
     * @return 查询到的文档数据
     */
    public List<CostmerVO> findByCondition(String collectionName, Integer age) {
        // 设置条件对象
        Criteria gteAgeCriteria = Criteria.where("age").gte(age);
        // 创建查询对象，并将条件对象添加到其中
        Query query = new Query(gteAgeCriteria);
        // 查询age大于等于条件的文档数据
        List<CostmerEntity> costmerEntities = mongoTemplate.find(query, CostmerEntity.class, collectionName);
        // 数据转换
        return costmerEntities.stream()
                .map(costmerEntity -> CostmerVO.builder()
                        .id(costmerEntity.getId())
                        .name(costmerEntity.getName())
                        .age(costmerEntity.getAge())
                        .hobbies(costmerEntity.getHobbies())
                        .build()).collect(Collectors.toList());
    }

    /**
     * 根据【条件】查询集合中【符合条件】的文档，获取其【文档列表】并【排序】
     *
     * @param collectionName 集合名称
     * @param age            年龄
     * @return 查询到并排序完的文档数据
     */
    public List<CostmerVO> findByConditionAndSort(String collectionName, Integer age) {
        // 设置条件对象
        Criteria lteAgeCriteria = Criteria.where("age").lte(age);
        // 创建查询对象，并将条件对象添加到其中，然后根据指定字段进行排序
        Query query = new Query(lteAgeCriteria).with(Sort.by("age"));
        // 执行查询
        List<CostmerEntity> costmerEntities = mongoTemplate.find(query, CostmerEntity.class, collectionName);
        // 数据转换
        return costmerEntities.stream()
                .map(costmerEntity -> CostmerVO.builder()
                        .id(costmerEntity.getId())
                        .name(costmerEntity.getName())
                        .age(costmerEntity.getAge())
                        .hobbies(costmerEntity.getHobbies())
                        .build()).collect(Collectors.toList());
    }

    /**
     * 根据【单个条件】查询集合中的文档数据，并【按指定字段进行排序】与【限制指定数目】
     *
     * @param collectionName 集合名称
     * @param sex            性别
     * @param limit          限定条数
     * @return 符合条件的文档列表
     */
    public List<CostmerVO> findByConditionAndSortLimit(String collectionName, String sex, Integer limit) {
        // 设置条件对象
        Criteria isSexCriteria = Criteria.where("sex").is(sex);
        // 创建查询对象，然后将条件对象添加到其中
        Query query = new Query(isSexCriteria).with(Sort.by("age")).limit(limit);
        // 执行查询
        List<CostmerEntity> costmerEntities = mongoTemplate.find(query, CostmerEntity.class, collectionName);
        // 数据转换
        return costmerEntities.stream()
                .map(costmerEntity -> CostmerVO.builder()
                        .id(costmerEntity.getId())
                        .name(costmerEntity.getName())
                        .age(costmerEntity.getAge())
                        .hobbies(costmerEntity.getHobbies())
                        .build()).collect(Collectors.toList());
    }

    /**
     * 根据【单个条件】查询集合中的文档数据，并【按指定字段进行排序】与【并跳过指定数目】
     *
     * @param collectionName 集合名称
     * @param sex            性别
     * @param skip           跳过的条数
     * @return 符合条件的文档列表
     */
    public List<CostmerVO> findByConditionAndSortSkip(String collectionName, String sex, Integer skip) {
        // 设置条件对象
        Criteria isSexCriteria = Criteria.where("sex").is(sex);
        // 创建查询对象，然后将条件对象添加到其中
        Query query = new Query(isSexCriteria).with(Sort.by("age")).skip(skip);
        // 执行查询操作
        List<CostmerEntity> costmerEntities = mongoTemplate.find(query, CostmerEntity.class, collectionName);
        // 数据转换
        return costmerEntities.stream()
                .map(costmerEntity -> CostmerVO.builder()
                        .id(costmerEntity.getId())
                        .name(costmerEntity.getName())
                        .age(costmerEntity.getAge())
                        .hobbies(costmerEntity.getHobbies())
                        .build()).collect(Collectors.toList());
    }

    /**
     * 查询【存在指定字段名称】的文档数据
     *
     * @param collectionName 集合名称
     * @param fieldName      字段名称
     * @return 符合条件的文档列表
     */
    public List<CostmerVO> findByExistsField(String collectionName, String fieldName) {
        // 设置条件对象
        Criteria existsFieldCriteria = Criteria.where(fieldName).exists(true);
        // 设置查询对象
        Query query = new Query(existsFieldCriteria);
        // 执行查询条件
        List<CostmerEntity> costmerEntities = mongoTemplate.find(query, CostmerEntity.class, collectionName);
        // 数据转换
        return costmerEntities.stream()
                .map(costmerEntity -> CostmerVO.builder()
                        .id(costmerEntity.getId())
                        .name(costmerEntity.getName())
                        .age(costmerEntity.getAge())
                        .hobbies(costmerEntity.getHobbies())
                        .build()).collect(Collectors.toList());
    }

    /**
     * 根据【AND】关联多个查询条件，查询集合中的文档数据
     *
     * @param collectionName 集合名称
     * @param age            年龄
     * @param sex            性别
     * @return 符合条件的文档列表
     */
    public List<CostmerVO> findByAndCondition(String collectionName, Integer age, String sex) {
        // 设置条件对象
        Criteria gteAgeCriteria = Criteria.where("age").gte(age);
        Criteria isSexCriteria = Criteria.where("sex").is(sex);
        // 创建条件对象，将上面条件进行 AND 关联
        Criteria criteria = new Criteria().andOperator(gteAgeCriteria, isSexCriteria);
        // 设置查询对象
        Query query = new Query(criteria);
        // 执行查询操作
        List<CostmerEntity> costmerEntities = mongoTemplate.find(query, CostmerEntity.class, collectionName);
        // 数据转换
        return costmerEntities.stream()
                .map(costmerEntity -> CostmerVO.builder()
                        .id(costmerEntity.getId())
                        .name(costmerEntity.getName())
                        .age(costmerEntity.getAge())
                        .hobbies(costmerEntity.getHobbies())
                        .build()).collect(Collectors.toList());
    }
}
