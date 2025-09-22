package com.thorekt.chatop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.thorekt.chatop.configuration.OpenApiConfig;
import com.thorekt.chatop.dto.request.MessageRequest;
import com.thorekt.chatop.dto.response.BaseResponse;
import com.thorekt.chatop.service.MessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

/**
 * Controller for message management
 * 
 * @author thorekt
 */
@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * Post a new message
     * 
     * @param request
     * @return BaseResponse indicating success or failure
     */
    @Operation(summary = "Post a new message", security = {
            @SecurityRequirement(name = OpenApiConfig.BEARER_SCHEME) })
    @PostMapping("/messages")
    public ResponseEntity<BaseResponse> postMessage(@RequestBody MessageRequest request) {

        try {
            messageService.createMessage(request.message(), request.user_id(), request.rental_id());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new BaseResponse(e.getMessage()));
        }

        return ResponseEntity.ok(new BaseResponse("Message send with success"));
    }
}
