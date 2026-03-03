package id.ac.ui.cs.advprog.eshop.controller;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductWriter;
import id.ac.ui.cs.advprog.eshop.service.ProductFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductWriter productWriter;

    @Autowired
    private ProductFinder productFinder;

    @GetMapping("/create")
    public String createProductPage(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "createProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product, Model model) {
        if (product.getProductName().isEmpty() || product.getProductQuantity() < 0) {
            model.addAttribute("errorMessage", "Product name cannot be empty and Product Quantity must not be negative! ");
            return "createProduct";
        }
        productWriter.create(product);
        return "redirect:list";
    }

    @GetMapping("/edit/{id}")
    public String editProductPage(@PathVariable String id, Model model) {
        Product product = productFinder.findById(id); 
        model.addAttribute("product", product);
        return "editProduct"; 
    }

    @PostMapping("/edit")
    public String editProductPost(@ModelAttribute Product product, Model model) {
        if (product.getProductName().isEmpty() || product.getProductQuantity() < 0) {
            model.addAttribute("errorMessage", "Product name cannot be empty and Product Quantity must not be negative! ");
            return "editProduct";
        }
        productWriter.edit(product);
        return "redirect:list"; 
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable String id) {
        productWriter.delete(id);
        return "redirect:/product/list";
    }

    @GetMapping("/list")
    public String productListPage(Model model) {
        List<Product> allProducts = productFinder.findAll();
        model.addAttribute("products", allProducts);
        return "productList";
    }

}
