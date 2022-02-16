package com.example.yammarket.service;

import com.example.yammarket.model.Bookmarks;
import com.example.yammarket.model.Posts;
import com.example.yammarket.model.Users;
import com.example.yammarket.repository.BookmarkRepository;
import com.example.yammarket.repository.PostRepository;
import com.example.yammarket.repository.UserRepository;
import com.example.yammarket.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<Long> getBookmarks(UserDetailsImpl userDetails) {
        //북마크DB에 전체 record조회
        List<Bookmarks> bookmarkList = bookmarkRepository.findAll();

        //postid 저장 List
        List<Long> postidList=new ArrayList<>();

        //반복문으로 로그인할 유저와 북마크 유저가 같으면 List에 add
        for(Bookmarks bookmarks:bookmarkList){
            if(bookmarks.getUserId().equals(userDetails.getUserId())){
                postidList.add(bookmarks.getId());
            }
        }
        return postidList;

//        List<Users> users = bookmarkRepository.findAllByUsers(userDetails.getUsers());
//        List<Posts> posts = null;
//        for(int i = 0; i< bookmarks.size(); i++) {
//            if (bookmarks.get(i).getUsers().equals(users)) {
//                posts.add(bookmarks.get(i).getPosts());
//            }
//        }
//        return posts;
    }
    

    public boolean registerBookmarks(@PathVariable Long postId,
                                     @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Posts posts = postRepository.findById(postId).orElseThrow(IllegalAccessError::new);

        String userId = userDetails.getUsers().getUserId();
        Bookmarks bookmarks = new Bookmarks(userId, posts.getId());
        bookmarkRepository.save(bookmarks);
        return true;
    }

    public boolean deleteBookmarks(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Bookmarks bookmarks = bookmarkRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        String bookmarkUserId = bookmarks.getUserId();
        String userId = userDetails.getUsers().getUserId();


        if (bookmarkUserId.equals(userId)) {
            bookmarkRepository.deleteById(id);
            return true;
        } else {
            return false;
        }

    }
}
