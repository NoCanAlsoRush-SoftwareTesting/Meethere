package lionel.meethere.news.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsCatalogDTO {

    private Integer id;

    private Integer adminId;

    private String title;

    private String image;

    private String createTime;
}
