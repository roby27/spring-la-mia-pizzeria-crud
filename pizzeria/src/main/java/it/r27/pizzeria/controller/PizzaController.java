package it.r27.pizzeria.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import it.r27.pizzeria.model.Pizza;
import it.r27.pizzeria.repository.PizzaRepository;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/pizzeria")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepo;

    @GetMapping
	public String index(Model model, @RequestParam(name = "keyword", required = false) String keyword) {
		
		List<Pizza> allPizza;
		
		if(keyword != null && !keyword.isBlank()) {
			allPizza = pizzaRepo.findByNameContaining(keyword);
			model.addAttribute("keyword", keyword);
		} else {
			allPizza = pizzaRepo.findAll();
		}

		model.addAttribute("pizza", allPizza);

		return "pizzeria/index";
	}

	@GetMapping("/show/{id}")
	public String show(@PathVariable(name = "id") Long id, @RequestParam(name = "keyword", required = false) String keyword,
			Model model) {

		Optional<Pizza> pizzaOptional = pizzaRepo.findById(id);

		if (pizzaOptional.isPresent()) {
			model.addAttribute("pizza", pizzaOptional.get());
		}
		
        model.addAttribute("keyword", keyword);
		if(keyword == null || keyword.isBlank() || keyword.equals("null")) {
			model.addAttribute("pizzaUrl", "/pizzeria");
		} else {			
			model.addAttribute("pizzaUrl", "/pizzeria?keyword=" + keyword);
		}

		return "pizzeria/show";
	}

	@GetMapping("/create")
	public String create (Model model) {
		model.addAttribute("pizza", new Pizza());
		return "pizzeria/create";
	}
	
	@PostMapping("/create")
	public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "/pizzeria/create";
		}

		pizzaRepo.save(formPizza);
		
		return "redirect:/pizzeria";
	}
	
}
