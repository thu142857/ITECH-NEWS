package com.itechnews.controller.web;

import com.itechnews.constant.MessageContant;
import com.itechnews.constant.MessageEnum;
import com.itechnews.entity.Comment;
import com.itechnews.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("admin/comment")
public class AdminCommentController {
    @Autowired
    CommentService commentService;

    @ModelAttribute
    public void commonObject(ModelMap modelMap) {
        modelMap.addAttribute("linkActive", "comment");
    }


    @GetMapping("index")
    public String index(@RequestParam(required = false) String q,
                        @RequestParam(required = false) Integer page,
                        @RequestParam(required = false) Integer postId,
                        ModelMap modelMap) {
        if (postId!=null){
            Page<Comment> commentPage = commentService.findCommentByPostId(postId,page);
            modelMap.addAttribute("commentPage", commentPage);
            modelMap.addAttribute("uri", "/admin/comment/index?postId="+postId);
        }
        else if (q == null) {
            Page<Comment> commentPage = commentService.findAll(page);
            modelMap.addAttribute("commentPage", commentPage);
            modelMap.addAttribute("uri", "/admin/comment/index");
        } else {
            Page<Comment> commentPage = commentService.findAllByContentContains(q, page);
            modelMap.addAttribute("q", q);
            modelMap.addAttribute("commentPage", commentPage);
            modelMap.addAttribute("uri", "/admin/comment/index?q="+q);
        }
        return "admin/comment/index";
    }

    @GetMapping("detail/{id}")
    public String index(@PathVariable("id") Integer commentId,
                        ModelMap modelMap) {
        Comment comment = commentService.findOneById(commentId);
        modelMap.addAttribute("comment", comment);
        return "admin/comment/detail";
    }
    @GetMapping("edit/{id}")
    public String edit(@PathVariable("id") Integer commentId,
                       ModelMap modelMap) {
        Comment comment = commentService.findOneById(commentId);
        modelMap.addAttribute("comment", comment);
        return "admin/comment/edit";
    }
    @PostMapping("edit/{id}")
    public String edit(@PathVariable("id") Integer commentId,@Valid @ModelAttribute("comment") Comment comment, BindingResult errors, RedirectAttributes ra) {
        Comment oldComment = commentService.findOneById(commentId);
        oldComment.setStatus(comment.getStatus());
        commentService.save(oldComment);
        ra.addFlashAttribute(MessageContant.ATTRIBUTE_NAME, MessageEnum.MSG_UPDATED_SUCCESSFULLY);
        return "redirect:/admin/comment/detail/" + comment.getId();
    }
    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer commentId, RedirectAttributes ra) {
        commentService.deleteById(commentId);
        ra.addFlashAttribute(MessageContant.ATTRIBUTE_NAME, MessageEnum.MSG_DELETED_SUCCESSFULLY);
        return "redirect:/admin/comment/index";
    }
}
