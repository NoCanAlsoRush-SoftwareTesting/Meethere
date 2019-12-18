package lionel.meethere.news.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsUpdateParam {

    @NotNull
    private Integer id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private String image;

}
