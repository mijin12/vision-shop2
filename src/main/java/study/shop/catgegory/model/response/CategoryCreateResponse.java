package study.shop.catgegory.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CategoryCreateResponse {
    private Long id;
    private String categoryName;
}
