package asia.rtx3090.controller;

import asia.rtx3090.service.ViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/view")
public class ViewController {

    private ViewService viewService;

    @Autowired
    public ViewController(ViewService viewService) {
        this.viewService = viewService;
    }

    /**
     * 创建视图
     *
     * @param collectionName 集合名称
     * @param viewName       视图名称
     * @return 视图创建结果
     */
    @GetMapping(value = "/createView")
    public String createView(String collectionName, String viewName) {
        return viewService.createView(collectionName, viewName);
    }

    /**
     * 删除视图
     *
     * @param viewName 视图名称
     * @return 视图删除结果
     */
    @GetMapping(value = "/dropView")
    public String dropView(String viewName) {
        return viewService.dropView(viewName);
    }
}
