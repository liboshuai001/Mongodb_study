package asia.rtx3090.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CostmerVO {

    private String id;

    private String name;

    private Integer age;

    private List<String> hobbies;

    private String otherField;
}
