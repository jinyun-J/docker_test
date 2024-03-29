package com.apartment.apart.domain.notice;

import com.apartment.apart.domain.user.SiteUser;
import com.apartment.apart.domain.user.UserService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {
    private final NoticeService noticeService;
    private final UserService userService;
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {
        Page<Notice> paging = this.noticeService.getList(page, kw);
        model.addAttribute("paging", paging);
        return "notice_list";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Notice notice = this.noticeService.getNotice(id);
        model.addAttribute("notice", notice);
        return "notice_detail";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String noticeCreate(NoticeForm noticeForm) {
        return "notice_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String noticeCreate(@Valid NoticeForm noticeForm, BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "notice_form";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.noticeService.create(noticeForm.getTitle(), noticeForm.getContent(), siteUser);
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String noticeModify(NoticeForm noticeForm, @PathVariable("id") Integer id, Principal principal) {
        Notice notice = this.noticeService.getNotice(id);
        if(!notice.getUser().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        noticeForm.setTitle(notice.getTitle());
        noticeForm.setContent(notice.getContent());
        return "notice_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String noticeModify(@Valid NoticeForm noticeForm, BindingResult bindingResult,
                               Principal principal, @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "notice_form";
        }
        Notice notice = this.noticeService.getNotice(id);
        if (!notice.getUser().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.noticeService.modify(notice, noticeForm.getTitle(), noticeForm.getContent());
        return String.format("redirect:/notice/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String noticeDelete(Principal principal, @PathVariable("id") Integer id) {
        Notice notice = this.noticeService.getNotice(id);
        if (!notice.getUser().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.noticeService.delete(notice);
        return "redirect:/";
    }
}