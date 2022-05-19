package asia.rtx3090.controller;

import asia.rtx3090.service.SaveService;
import asia.rtx3090.vo.CostmerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/save")
public class SaveController {

    private SaveService saveService;

    @Autowired
    public SaveController(SaveService saveService) {
        this.saveService = saveService;
    }

    /**
     * 存储【一条】用户信息，如果文档信息已经【存在就执行更新】
     *
     * @param costmerVO      用户信息
     * @param collectionName 集合名称
     * @return 保存的文档信息
     */
    @PostMapping("/save")
        public CostmerVO save(@RequestBody CostmerVO costmerVO, String collectionName) {
        return saveService.save(costmerVO, collectionName);
    }
}
