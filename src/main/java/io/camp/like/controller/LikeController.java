package io.camp.like.controller;

import io.camp.like.model.dto.LikeRequestDTO;
import io.camp.like.service.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/like")
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    public void createLike(@RequestBody @Valid LikeRequestDTO likeRequestDTO) throws Exception {
        likeService.createLike(likeRequestDTO);
    }

    @DeleteMapping
    public void deleteLike(@RequestBody @Valid LikeRequestDTO likeRequestDTO) throws Exception {
        likeService.deleteLike(likeRequestDTO);
    }
}
