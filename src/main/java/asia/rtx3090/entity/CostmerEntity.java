package asia.rtx3090.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Data
@Document
@Builder
public class CostmerEntity {

    @MongoId
    private String id;

    private String name;

    private Integer age;

    @Field("likes")
    private List<String> hobbies;

    @Transient
    private String otherField;
}
