package app.controller;

import app.jms.Producer;
import app.model.*;
import app.service.CartService;
import app.service.CategoryService;
import app.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Controller
@EnableWebMvc
@RequestMapping("/products")
public class ProductController {

    public static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private int page;
    private ProductService productService;
    private CategoryService categoryService;
    private CartService cartService;
    @Qualifier("categoryValidator")
    private Validator categoryValidator;
    @Qualifier("productValidator")
    private Validator productValidator;

    @Autowired
    public void setProductValidator(Validator productValidator) {
        this.productValidator = productValidator;
    }

    @Autowired
    public void setCategoryValidator(Validator categoryValidator) {
        this.categoryValidator = categoryValidator;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/buy/{id}")
    public void listProductHandler(HttpServletRequest request, HttpServletResponse response,
                                   @PathVariable(value = "id") Integer id) {
        cartService.addProduct(request, response, id);
    }

    @GetMapping
    public ModelAndView allProducts(@RequestParam(defaultValue = "1") int page){
        this.page = page;
        List<Product> products = productService.allProducts(page, null);
        return getProducts(products);
    }

    @PostMapping("/filter")
    public ModelAndView allProducts(ProductFilter filter) {
        List<Product> products = productService.allProducts(page, filter);
        return getProducts(products);
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editPage(@PathVariable("id") int id){
        List<Category> categories = categoryService.allCategories();
        Product product = productService.getProductById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editPage");
        modelAndView.addObject("product", product);
        modelAndView.addObject("categoryList", categories);
        return modelAndView;
    }

    @PostMapping("/edit")
    public ModelAndView editProduct(@ModelAttribute("product") Product product){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/products/?page=" + this.page);
        productService.edit(product);
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView addPage(){
        List<Category> categories = categoryService.allCategories();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editPage");
        modelAndView.addObject("categoryList", categories);
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addProduct(@Valid @ModelAttribute("product") Product product, BindingResult bindingResult){
        List<Category> categories = categoryService.allCategories();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("categoryList", categories);
        productValidator.validate(product, bindingResult);
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("editPage");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/products/?page=" + this.page);
        try {
            int resultId = productService.add(product);
            log.info("Created product with id {}", resultId);
            Producer.produceMessage();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("/addCategory")
    public ModelAndView addCategoryPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addCategory");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping("/addCategory")
    public ModelAndView addCategory(@Valid @ModelAttribute("category") Category category, BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();
        categoryValidator.validate(category, bindingResult);
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("/addCategory");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/products/?page=" + this.page);
        try {
            int resultId = categoryService.add(category);
            log.info("Created category with id {}", resultId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable("id") int id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/products/?page=" + this.page);
        Product product = productService.getProductById(id);
        productService.delete(product);
        return modelAndView;
    }

    private ModelAndView getProducts(List<Product> products) {
        List<Category> categories = categoryService.allCategories();
        int productsCount = productService.productCount();
        int pagesCount = (productsCount + 9) / 10;
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("products");
        modelAndView.addObject("page", page);
        modelAndView.addObject("productList", products);
        modelAndView.addObject("productsCount", productsCount);
        modelAndView.addObject("pagesCount", pagesCount);
        modelAndView.addObject("categoryList", categories);
        return modelAndView;
    }
}
