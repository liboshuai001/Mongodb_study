package asia.rtx3090.controller;

import asia.rtx3090.service.CreateCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/createCollection")
public class CreateCollectionController {

    private CreateCollectionService createCollectionService;

    @Autowired
    public CreateCollectionController(CreateCollectionService createCollectionService) {
        this.createCollectionService = createCollectionService;
    }

    /**
     * 创建普通的集合
     *
     * @param collectionName 集合名称
     * @return 创建结果
     */
    @GetMapping("/createCollection")
    public String createCollection(String collectionName) {
        return createCollectionService.createCollection(collectionName);
    }

    /**
     * 创建固定大小的集合
     *
     * @param collectionName 集合名称
     * @return 创建结果
     */
    @GetMapping("/createCollectionFixedSize")
    public String createCollectionFixedSize(String collectionName) {
        return createCollectionService.createCollectionFixedSize(collectionName);
    }

    /**
     * 创建带有验证条件的集合
     *
     * @param collectionName 集合名称
     * @return 创建结果
     */
    @GetMapping("/createCollectionValidation")
    public String createCollectionValidation(String collectionName) {
        return createCollectionService.createCollectionValidation(collectionName);
    }
}
