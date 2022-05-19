package asia.rtx3090.controller;

import asia.rtx3090.service.QueryCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/queryCollection")
public class QueryCollectionController {

    private QueryCollectionService queryCollectionService;

    @Autowired
    public QueryCollectionController(QueryCollectionService queryCollectionService) {
        this.queryCollectionService = queryCollectionService;
    }

    /**
     * 获取所有集合名称
     *
     * @return 集合名称列表
     */
    @GetMapping("/getCollectionNames")
    public List<String> getCollectionNames() {
        return queryCollectionService.getCollectionNames();
    }

    /**
     * 检查指定集合名称是否存在
     *
     * @param collectionName 集合名称
     * @return 检查结果
     */
    @GetMapping("checkCollectionExists")
    public String checkCollectionExists(String collectionName) {
        return queryCollectionService.checkCollectionExists(collectionName);
    }
}
