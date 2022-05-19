package asia.rtx3090.service;

import asia.rtx3090.dto.CostmerDTO;
import asia.rtx3090.vo.CostmerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class SaveService {

    private MongoTemplate mongoTemplate;

    @Autowired
    public SaveService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 存储【一条】用户信息，如果文档信息已经【存在就执行更新】
     *
     * @param costmerVO      用户信息
     * @param collectionName 集合名称
     * @return 保存的文档信息
     */
    public CostmerVO save(CostmerVO costmerVO, String collectionName) {
        // 设置用户信息
        CostmerDTO costmerDTO = CostmerDTO.builder()
                .id(costmerVO.getId())
                .name(costmerVO.getName())
                .age(costmerVO.getAge())
                .hobbies(costmerVO.getHobbies())
                .build();
        // 存储用户信息,如果文档信息已经存在就执行更新
        CostmerDTO saveCostmer = mongoTemplate.save(costmerDTO, collectionName);
        // 转化数据
        return CostmerVO.builder()
                .id(saveCostmer.getId())
                .name(saveCostmer.getName())
                .age(saveCostmer.getAge())
                .hobbies(saveCostmer.getHobbies())
                .build();
    }

}
