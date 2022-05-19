package asia.rtx3090.service;

import asia.rtx3090.entity.CostmerEntity;
import asia.rtx3090.vo.CostmerVO;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Auther: Bernardo
 * @Date: 2022/5/19 21:50
 * @Description: 删除文档数据
 */
@Service
public class RemoveService {

    private MongoTemplate mongoTemplate;

    @Autowired
    public RemoveService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 删除集合中【符合条件】的【一个】或【多个】文档
     *
     * @param collectionName 集合名称
     * @return 删除结果
     */
    public String remove(String collectionName) {
        // 设置条件对象
        Criteria isIdCriteria = Criteria.where("id").is("1");
        // 设置查询对象
        Query query = new Query(isIdCriteria);
        // 执行删除查找到的匹配的全部文档信息
        DeleteResult remove = mongoTemplate.remove(query, CostmerEntity.class, collectionName);
        // 输出结果信息
        return "成功删除 " + remove.getDeletedCount() + " 条文档信息";
    }

    /**
     * 删除【符合条件】的【单个文档】，并返回删除的文档。
     *
     * @param collectionName 集合名称
     * @return 删除的用户信息
     */
    public CostmerVO findAndRemove(String collectionName) {
        // 设置条件对象
        Criteria isIdCriteria = Criteria.where("id").is("1");
        // 设置查询对象
        Query query = new Query(isIdCriteria);
        // 执行删除查找到的匹配的第一条文档,并返回删除的文档信息
        CostmerEntity andRemove = mongoTemplate.findAndRemove(query, CostmerEntity.class, collectionName);
        // 数据转化
        return CostmerVO.builder()
                .id(andRemove.getId())
                .name(andRemove.getName())
                .age(andRemove.getAge())
                .hobbies(andRemove.getHobbies())
                .build();
    }

    /**
     * 删除【符合条件】的【全部文档】，并返回删除的文档。
     *
     * @param collectionName 集合名称
     * @return 删除的用户信息
     */
    public List<CostmerVO> findAllAndRemove(String collectionName) {
        // 设置条件对象
        Criteria isIdCriteria = Criteria.where("id").is("1");
        // 设置查询对象
        Query query = new Query(isIdCriteria);
        // 执行删除查找到的匹配的全部文档,并返回删除的全部文档信息
        List<CostmerEntity> costmerEntities = mongoTemplate.findAllAndRemove(query, CostmerEntity.class, collectionName);
        // 数据转化
        return costmerEntities.stream()
                .map(costmerEntity -> CostmerVO.builder()
                .id(costmerEntity.getId())
                .name(costmerEntity.getName())
                .age(costmerEntity.getAge())
                .hobbies(costmerEntity.getHobbies())
                .build())
                .collect(Collectors.toList());
    }


}
