package lionel.meethere.news.param;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsUpdateParam {

    private Integer id;

    private String title;

    private String content;

    private String image;

}
