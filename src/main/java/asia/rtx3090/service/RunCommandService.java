package asia.rtx3090.service;

import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * @Auther: Bernardo
 * @Date: 2022/5/20 00:36
 * @Description: RunCommand 命令
 */
@Slf4j
@Service
public class RunCommandService {

    private MongoTemplate mongoTemplate;

    @Autowired
    public RunCommandService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 执行 mongoDB 自定义命令，详情可以查看：https://docs.mongodb.com/manual/reference/command/
     *
     * @return 执行命令返回结果的 Json 结果
     */
    public Object runCommand() {
        // 自定义命令
        String jsonCommand = "{\"buildInfo\":1}";
        // 将 JSON 字符串解析成 MongoDB 命令
        Document bson = Document.parse(jsonCommand);
        // 执行自定义命令
        return mongoTemplate.getDb().runCommand(bson);
    }
}
