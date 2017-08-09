package com.sadabasi.diary.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sadabasi.diary.config.DiaryException;
import com.sadabasi.diary.domain.Entry;
import com.sadabasi.diary.domain.User;
import com.sadabasi.diary.service.EntryService;
import com.sadabasi.diary.util.SecurityUtil;

/**
 * entry controller, add, delete, update
 * 
 * @author sevilay.adabasi
 * */

@Controller
public class EntryController {

	private static Logger logger = Logger.getLogger(EntryController.class);

	@Autowired
	EntryService service;

	@GetMapping("/")
	String index(Model model) {
		return "redirect:/entry";
	}

	@GetMapping("/entry")
	String getEntries(Model model, RedirectAttributes redirectAttributes) {
		try {
			User loginUser = SecurityUtil.getLoginUser();
			Iterable<Entry> list = service.findByUser(loginUser);
			model.addAttribute("entries", list);
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("alertDanger", e.getMessage());
			if(!(e instanceof DiaryException)) {
				logger.error(e);
			}
		}
		return "entry";
	}

	@PostMapping("/entry")
	String addEntry(String text, String dueDate, RedirectAttributes redirectAttributes) {
		try {
			User loginUser = SecurityUtil.getLoginUser();
			Entry entry = service.addEntry(loginUser, text);
			redirectAttributes.addFlashAttribute("alertSuccess", "Diary created by user.");
			logger.info(String.format("An entry with id=%s added by user: %s", entry.getId(), loginUser));
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("alertDanger", e.getMessage());
			if(!(e instanceof DiaryException)) {
				logger.error(e);  
			}
		}
		return "redirect:/entry";
	}

	@PostMapping("/entry/{id}")
	String updateEntry(@PathVariable Long id, @ModelAttribute(name="value") String value, @ModelAttribute(name="dueDate") String dueDate, RedirectAttributes redirectAttributes) {
		try {
			User loginUser = SecurityUtil.getLoginUser();
			service.updateEntry(id, value);
			redirectAttributes.addFlashAttribute("alertSuccess", "Todo updated by user " +value);
			logger.info(String.format("An entry with id=%s updated by user: %s", id, loginUser));
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("alertDanger", e.getMessage());
			if(!(e instanceof DiaryException)) {
				logger.error(e);
			}
		}
		return "redirect:/entry";
	}

	@DeleteMapping("/entry/{id}")
	String deleteEntry(@PathVariable Long id, RedirectAttributes redirectAttributes) {
		try {
			User loginUser = SecurityUtil.getLoginUser();
			service.deleteEntry(id);
			redirectAttributes.addFlashAttribute("alertSuccess", "Diary deleted by user. ");
			logger.info(String.format("An entry with id=%s deleted by user: %s", id, loginUser));
		} catch (Exception e) {
			redirectAttributes.addFlashAttribute("alertDanger", e.getMessage());
			if(!(e instanceof DiaryException)) {
				logger.error(e);
			}
		}
		return "redirect:/entry";
	}

}