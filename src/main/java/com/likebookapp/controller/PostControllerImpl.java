package com.likebookapp.controller;

import com.likebookapp.model.dto.AddPostDTO;
import com.likebookapp.service.PostService;
import com.likebookapp.util.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PostControllerImpl implements PostController{

    private final LoggedUser loggedUser;

    private final PostService postService;

    public PostControllerImpl(LoggedUser loggedUser, PostService postService) {
        this.loggedUser = loggedUser;
        this.postService = postService;
    }


    @Override
    public String addPost() {
        if (!loggedUser.isLogged()) {
            return "redirect:/users/login";
        }

        return "post-add";
    }

    @Override
    public String addPost(AddPostDTO addPostDTO, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes
                    .addFlashAttribute("addPostDTO", addPostDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.addPostDTO", result);

            return "redirect:/posts/add-post";
        }

        addPostDTO.setId(loggedUser.getId());
        this.postService.addPost(addPostDTO);
        return "redirect:/home";
    }

    @Override
    public String likePost(Long id) {
        postService.likePostWithId(id, loggedUser.getId());
        return "redirect:/home";
    }

    @Override
    public String removePost(Long id) {
        postService.removePostById(id);

        return "redirect:/home";
    }

    @ModelAttribute
    public AddPostDTO addPostDTO() {
        return new AddPostDTO();
    }
}

