package asia.rtx3090.service;

import asia.rtx3090.dto.CostmerDTO;
import asia.rtx3090.vo.CostmerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InsertService {

    private MongoTemplate mongoTemplate;

    @Autowired
    public InsertService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 插入【多条】文档数据，如果文档信息已经【存在就抛出异常】
     * @param costmerVOS 用户信息
     * @param collectionName 集合名称
     * @return 插入的文档信息
     */
    public List<CostmerVO> insertMany(List<CostmerVO> costmerVOS, String collectionName) {
        // 设置用户信息
        List<CostmerDTO> costmerDTOS = costmerVOS.stream()
                .map(costmerVO -> CostmerDTO.builder()
                .id(costmerVO.getId())
                .name(costmerVO.getName())
                .age(costmerVO.getAge())
                .hobbies(costmerVO.getHobbies())
                .build())
                .collect(Collectors.toList());
        // 插入一条用户数据，如果某个文档信息已经存在就抛出异常
        Collection<CostmerDTO> costmerDTOCollection = mongoTemplate.insert(costmerDTOS, collectionName);
        // 进行数据转化
        return costmerDTOCollection.stream()
                .map(costmerDTO -> CostmerVO.builder()
                .id(costmerDTO.getId())
                .name(costmerDTO.getName())
                .age(costmerDTO.getAge())
                .hobbies(costmerDTO.getHobbies())
                .build())
                .collect(Collectors.toList());
    }

    /**
     * 插入【一条】文档数据，如果文档信息已经【存在就抛出异常】
     *
     * @param costmerVO      用户信息
     * @param collectionName 集合名称
     * @return 插入的文档信息
     */
    public CostmerVO insert(CostmerVO costmerVO, String collectionName) {
        // 设置用户信息
        CostmerDTO costmerDTO = CostmerDTO.builder()
                .id(costmerVO.getId())
                .name(costmerVO.getName())
                .age(costmerVO.getAge())
                .hobbies(costmerVO.getHobbies())
                .build();
        // 插入一条用户数据，如果文档信息已经存在就抛出异常
        CostmerDTO insertCostmer = mongoTemplate.insert(costmerDTO, collectionName);
        // 进行数据转换
        return CostmerVO.builder()
                .id(insertCostmer.getId())
                .name(insertCostmer.getName())
                .age(insertCostmer.getAge())
                .hobbies(insertCostmer.getHobbies())
                .build();
    }
}
