package study.shop.catgegory.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import study.shop.catgegory.Repository.CategoryRepository;
import study.shop.catgegory.model.entity.Category;
import study.shop.catgegory.model.exception.CategoryNameDuplicationException;
import study.shop.catgegory.model.exception.CategoryNotFoundException;
import study.shop.catgegory.model.request.CategoryResponse;
import study.shop.catgegory.model.request.CategoryUpdateRequest;
import study.shop.catgegory.model.request.CreateCategoryRequest;
import study.shop.catgegory.model.response.CategoryCreateResponse;
import study.shop.catgegory.model.response.CategoryListResponse;

import java.awt.print.Pageable;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryCreateResponse create(CreateCategoryRequest request){
        //1. 카테고리 이름 중복 검사
        if(categoryRepository.existsByCategoryName(request.getCategoryName()))
            throw new CategoryNameDuplicationException();

        //2. 카테고리 생성
        Category category = Category.create(request);
        Category createCategory = categoryRepository.save(category);
        return new CategoryCreateResponse(
                createCategory.getId(),
                createCategory.getCategoryName());
    }

    public Page<CategoryListResponse> getCategoryies(int page){
        PageRequest request = PageRequest.of(page, 10);
        Page<Category> categoryList =
                categoryRepository.findAll(request);

        return categoryList
                .map( category ->
                        new CategoryListResponse(
                                category.getId(),
                                category.getCategoryName())
                );
    }

    public CategoryResponse findCategory(Long id){
        Category category
                = categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);
        return new CategoryResponse(category.getId(), category.getCategoryName());
    }
    public void update(Long id, CategoryUpdateRequest request){

        //1.해당 카테고리가 존재하는지 검증
        Category category
                = categoryRepository.findById(id)
                .orElseThrow(CategoryNotFoundException::new);;
        //2.해당 카테고리명 중복 검사
        if(categoryRepository.existsByCategoryNameAndIdNot(
                request.getCategoryName(),id)
        )
            throw new CategoryNameDuplicationException();
        //3.카테고리 이름 수정
        category.update(request.getCategoryName());
        categoryRepository.save(category);
    }

}
