package asia.rtx3090.controller;

import asia.rtx3090.service.RemoveCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/removeCollection")
public class RemoveCollectionController {

    private RemoveCollectionService removeCollectionService;

    @Autowired
    public RemoveCollectionController(RemoveCollectionService removeCollectionService) {
        this.removeCollectionService = removeCollectionService;
    }

    /**
     * 删除指定名称的集合
     *
     * @param collectionName 集合名称
     * @return 删除结果
     */
    @GetMapping("/dropCollection")
    public String dropCollection(String collectionName) {
        return removeCollectionService.dropCollection(collectionName);
    }
}
