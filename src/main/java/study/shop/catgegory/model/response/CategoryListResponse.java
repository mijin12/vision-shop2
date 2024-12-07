package study.shop.catgegory.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CategoryListResponse {
    private Long id;
    private String categoryName;
}
