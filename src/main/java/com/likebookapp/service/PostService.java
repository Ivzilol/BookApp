package com.likebookapp.service;

import com.likebookapp.model.dto.AddPostDTO;
import com.likebookapp.model.dto.PostWithUsernamesDTO;
import com.likebookapp.model.entity.Mood;
import com.likebookapp.model.entity.Post;
import com.likebookapp.model.entity.User;
import com.likebookapp.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;

    private final MoodServiceImpl moodService;

    private final UserService userService;

    public PostService(PostRepository postRepository, MoodServiceImpl moodService, UserService userService) {
        this.postRepository = postRepository;
        this.moodService = moodService;
        this.userService = userService;
    }

    public Set<Post> getPostsFromCurrentUser(Long id) {
        return postRepository.findAllByUserId(id);
    }

    public Set<PostWithUsernamesDTO> getPostsFromOtherUsers(Long id) {
        Set<Post> postsByUserIdNot = postRepository.findPostByUserIdNot(id);

        return mapToPostWithUsernameDTO(postsByUserIdNot);
    }

    private Set<PostWithUsernamesDTO> mapToPostWithUsernameDTO(Set<Post> postsByUserIdNot) {
        return postsByUserIdNot.stream()
                .map(e -> {
                    PostWithUsernamesDTO currentDTO = new PostWithUsernamesDTO();
                    currentDTO.setContent(e.getContent())
                            .setId(e.getId())
                            .setLikes(e.getLikes())
                            .setUserLikes(e.getUserLikes())
                            .setMood(e.getMood().getMoodName())
                            .setUsername(e.getUser().getUsername());
                            return currentDTO;
                })
                .collect(Collectors.toSet());
    }

    public void addPost(AddPostDTO addPostDTO) {
        this.postRepository.save(this.mapPost(addPostDTO));
    }

    private Post mapPost(AddPostDTO addPostDTO) {
        Post post = new Post();
        Mood mood = this.moodService.findMood(addPostDTO.getMood());
        User userById = userService.findUserById(addPostDTO.getId()).orElse(null);

        post.setMood(mood);
        post.setContent(addPostDTO.getContent());
        post.setUser(userById);
        return post;
    }

    public void likePostWithId(Long postId, Long userId) {
        Post post = postRepository.findById(postId).orElse(null);
        User user = userService.findUserById(userId).orElse(null);
        assert post != null;
        post.getUserLikes().add(user);
        post.setLikes(post.getLikes() + 1);
        postRepository.save(post);
    }

    public void removePostById(Long id) {
        postRepository.deleteById(id);
    }
}
