package com.likebookapp.controller;

import com.likebookapp.model.dto.PostWithUsernamesDTO;
import com.likebookapp.model.entity.Post;
import com.likebookapp.model.entity.User;
import com.likebookapp.service.PostService;
import com.likebookapp.service.UserService;
import com.likebookapp.util.LoggedUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping(name = "/")
public class HomeController {

    private final LoggedUser loggedUser;

    private final PostService postService;

    private final UserService userService;


    public HomeController(LoggedUser loggedUser, PostService postService, UserService userService) {
        this.loggedUser = loggedUser;
        this.postService = postService;
        this.userService = userService;
    }

    @GetMapping String index() {
        if (loggedUser.isLogged()) {
            return "redirect:/home";
        }
        return "index";
    }

    @GetMapping("/home")
    String home(Model model) {
        if (!loggedUser.isLogged()) {
            return "redirect:/";
        }

        User user = userService.findUserById(loggedUser.getId()).orElse(null);

        model.addAttribute("currentUserInfo", user);
        Set<Post> postsFromCurrentUser = this.postService.getPostsFromCurrentUser(
                this.loggedUser.getId());
        model.addAttribute("userPosts", postsFromCurrentUser);
        Set<PostWithUsernamesDTO> postFromOtherUsers = postService.getPostsFromOtherUsers(
                this.loggedUser.getId());
        model.addAttribute("otherUserPosts", postFromOtherUsers);
        model.addAttribute("user", user);

        return "home";
    }
}
