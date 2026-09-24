package jp.co.goodworks.inquiry.web;

import jakarta.validation.Valid;
import jp.co.goodworks.inquiry.domain.InquiryStatus;
import jp.co.goodworks.inquiry.service.InquiryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class InquiryController {

    private final InquiryService inquiryService;

    public InquiryController(InquiryService inquiryService) {
        this.inquiryService = inquiryService;
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/inquiries";
    }

    @GetMapping("/inquiries")
    public String list(@RequestParam(defaultValue = "") String keyword,
                       @RequestParam(required = false) InquiryStatus status,
                       Model model) {
        model.addAttribute("inquiries", inquiryService.findAll(keyword, status));
        model.addAttribute("keyword", keyword);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("statuses", InquiryStatus.values());
        return "inquiries/list";
    }

    @GetMapping("/inquiries/new")
    public String newForm(Model model) {
        model.addAttribute("inquiryForm", new InquiryForm());
        model.addAttribute("statuses", InquiryStatus.values());
        return "inquiries/form";
    }

    @PostMapping("/inquiries")
    public String create(@Valid @ModelAttribute InquiryForm inquiryForm,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("statuses", InquiryStatus.values());
            return "inquiries/form";
        }

        inquiryService.create(
                inquiryForm.getTitle(),
                inquiryForm.getContent(),
                inquiryForm.getStatus());
        return "redirect:/inquiries";
    }
}
