package study.shop.catgegory.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import study.shop.catgegory.model.request.CreateCategoryRequest;

@Builder
@Entity(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name", nullable = false)
    private String categoryName;

    public static Category create(CreateCategoryRequest request) {
        return Category.builder()
                .categoryName(request.getCategoryName())
                .build();
    }

    public void update(String categoryName) {
        this.setCategoryName(categoryName);
    }
}
