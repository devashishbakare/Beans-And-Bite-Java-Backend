package com.beansAndBite.beansAndBite.controller;

import com.beansAndBite.beansAndBite.dto.AddToFavoriteRequestDTO;
import com.beansAndBite.beansAndBite.service.FavoriteService;
import com.beansAndBite.beansAndBite.util.BaseResponse;
import com.beansAndBite.beansAndBite.util.ErrorInfo;
import com.beansAndBite.beansAndBite.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping("/favorite")
@Slf4j
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService){
        this.favoriteService = favoriteService;
    }

    @PutMapping("/addFavorites")
    public ResponseEntity<BaseResponse> addToFavorites(@RequestBody AddToFavoriteRequestDTO request){
        log.info("sending request from controller");
        boolean isFavouritesAdded = favoriteService.addToFavorites(request.getFavorites());
        if(isFavouritesAdded){
            Response<String> response = new Response<>("favorites has been added", null);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            ErrorInfo errorResponse = new ErrorInfo(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Something went wrong", LocalDateTime.now().toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
