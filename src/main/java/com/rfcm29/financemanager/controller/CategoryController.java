package com.rfcm29.financemanager.controller;

import com.rfcm29.financemanager.entity.Category;
import com.rfcm29.financemanager.repository.CategoryRepository;
import com.rfcm29.financemanager.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @GetMapping
    public List<Category> getAll(@AuthenticationPrincipal UserDetails user) {
        var u = userRepository.findByEmail(user.getUsername()).orElseThrow();
        return categoryRepository.findAllAvailableForUser(u.getId());
    }
}
