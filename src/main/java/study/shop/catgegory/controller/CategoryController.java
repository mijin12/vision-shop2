package study.shop.catgegory.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import study.shop.catgegory.model.request.CategoryResponse;
import study.shop.catgegory.model.request.CategoryUpdateRequest;
import study.shop.catgegory.model.request.CreateCategoryRequest;
import study.shop.catgegory.model.response.CategoryListResponse;
import study.shop.catgegory.service.CategoryService;

import java.util.List;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("")
    public String getCategoryList(Model model,@RequestParam(value = "page",defaultValue = "0")int page) {

        Page<CategoryListResponse> categories
                = categoryService.getCategoryies(page);
        model.addAttribute("categories", categories);
        return "category/list";
    }

    @GetMapping("/new-category")
    public String getCategoryForm(Model model) {
        model.addAttribute("modelTitle","카테고리 등록하기" );
        return "category/category-form";
    }

    @PostMapping("/new-category")
    public String createCategory(@ModelAttribute @Valid CreateCategoryRequest request,
    BindingResult result
    ) {
        //1.카테고리 생성 실패 시에 오류 반환
        if(result.hasErrors())
            throw new IllegalArgumentException(
                    result.getAllErrors().get(0).getDefaultMessage()
            );

        //2.카테고리 생성 성공 시에 카테고리 목록 페이지로 리다이렉트
        categoryService.create(request);
        return "redirect:/categories";
    }

    @GetMapping("/update-category/{id}")
    public String updateCategoryForm(Model model, @PathVariable Long id) {

            CategoryResponse category = categoryService.findCategory(id);
            model.addAttribute("category", category);
            model.addAttribute("modleTitle", "카테고리 수정하기");
            return "category/category-form";
    }

    @PostMapping("/update-category/{id}")
    public String updateCategory(@PathVariable Long id, @ModelAttribute @Valid CategoryUpdateRequest request,BindingResult result){

        if(result.hasErrors())
            throw new IllegalArgumentException(
                    result.getAllErrors().get(0).getDefaultMessage()
            );

        //1.카테고리 수정 성공시 목록으로 리다이렉트
        categoryService.update(id, request);
        return "redirect:/categories";
    }



}