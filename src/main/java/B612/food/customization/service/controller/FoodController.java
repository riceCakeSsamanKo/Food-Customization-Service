package B612.food.customization.service.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FoodController {
    @GetMapping("/food")
    public String getFood(Model model) {
        return "food/search";
    }
}
