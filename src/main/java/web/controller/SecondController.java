package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.model.Car;

import java.util.ArrayList;
import java.util.List;

@Controller
public class SecondController {


    @GetMapping("/cars")
    public String carsCount(@RequestParam(value = "count", required = false) String count, Model model) {
        if (count != null) {
        List<Car> messages = carsConts(count);
        model.addAttribute("messages", messages);
        }
        return "first/cars";
    }

    public List<Car> carsConts(String count) {
        int counts = Integer.parseInt(count);
        List<Car> result = new ArrayList<>();
        List<Car> originList = new ArrayList<>();
        originList.add(new Car("Ford", "Russia", 2020));
        originList.add(new Car("Ford", "USA", 2021));
        originList.add(new Car("KIA", "Africa", 2019));
        originList.add(new Car("KIA", "France", 2022));
        originList.add(new Car("GAZel", "India", 2021));

        if (counts >= originList.size()) {
            return originList;
        } else {
            for (int i = 0; i < counts; i++) {
                result.add(originList.get(i));
            }
        }
        return result;
    }
}
