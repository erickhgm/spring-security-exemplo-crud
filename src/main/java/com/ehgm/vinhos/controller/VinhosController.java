package com.ehgm.vinhos.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ehgm.vinhos.model.TipoVinho;
import com.ehgm.vinhos.model.Vinho;
import com.ehgm.vinhos.repository.Vinhos;
import com.ehgm.vinhos.repository.filter.VinhoFilter;

@Controller
@RequestMapping("/vinhos")
public class VinhosController {

	@Autowired
	private Vinhos vinhos;

	@GetMapping("/novo")
	public ModelAndView novo(Vinho vinho) {
		ModelAndView modelAndView = new ModelAndView("vinho/cadastro-vinho");
		modelAndView.addObject(vinho);
		modelAndView.addObject("tipos", TipoVinho.values());
		return modelAndView;
	}

	@PostMapping("/novo")
	public ModelAndView salvar(@Valid Vinho vinho, BindingResult results, RedirectAttributes redirectAttributes) {
		if (results.hasErrors()) {
			return novo(vinho);
		}
		vinhos.save(vinho);
		redirectAttributes.addFlashAttribute("mensagem", "Vinho salvo com sucesso!");
		return new ModelAndView("redirect:/vinhos/novo");
	}

	@GetMapping
	public ModelAndView pesquisar(VinhoFilter vinhoFilter) {
		ModelAndView modelAndView = new ModelAndView("vinho/pesquisa-vinhos");
		modelAndView.addObject("vinhos",
				vinhos.findByNomeContainingIgnoreCase(Optional.ofNullable(vinhoFilter.getNome()).orElse("%")));
		return modelAndView;
	}

	@GetMapping("/{codigo}")
	public ModelAndView editar(@PathVariable Long codigo) {
		Vinho vinho = vinhos.findOne(codigo);
		return novo(vinho);
	}

	@DeleteMapping("/{codigo}")
	public String apagar(@PathVariable Long codigo, RedirectAttributes redirectAttributes) {
		vinhos.delete(codigo);
		redirectAttributes.addFlashAttribute("mensagem", "Vinho removido com sucesso");
		return "redirect:/vinhos";
	}

}