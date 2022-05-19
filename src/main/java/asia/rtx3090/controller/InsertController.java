package asia.rtx3090.controller;

import asia.rtx3090.service.InsertService;
import asia.rtx3090.vo.CostmerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/insert")
public class InsertController {

    private InsertService insertService;

    @Autowired
    public InsertController(InsertService insertService) {
        this.insertService = insertService;
    }

    /**
     * 插入【一条】文档数据，如果文档信息已经【存在就抛出异常】
     *
     * @param costmerVO      用户信息
     * @param collectionName 集合名称
     * @return 插入的文档信息
     */
    @PostMapping(path = "/insert")
    public CostmerVO insert(@RequestBody CostmerVO costmerVO, @RequestParam String collectionName) {
        return insertService.insert(costmerVO, collectionName);
    }

    /**
     * 插入【多条】文档数据，如果文档信息已经【存在就抛出异常】
     *
     * @param costmerVOS     用户信息
     * @param collectionName 集合名称
     * @return 插入的文档信息
     */
    @PostMapping(path = "/insertMany")
    public List<CostmerVO> insertMany(@RequestBody List<CostmerVO> costmerVOS, String collectionName) {
        return insertService.insertMany(costmerVOS, collectionName);
    }
}
