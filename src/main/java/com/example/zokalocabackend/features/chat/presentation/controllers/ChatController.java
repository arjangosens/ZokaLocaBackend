package com.example.zokalocabackend.features.chat.presentation.controllers;

import com.example.zokalocabackend.features.chat.domain.Conversation;
import com.example.zokalocabackend.features.chat.domain.Message;
import com.example.zokalocabackend.features.chat.presentation.datatransferobjects.ConversationItemDTO;
import com.example.zokalocabackend.features.chat.presentation.datatransferobjects.MessageDTO;
import com.example.zokalocabackend.features.chat.presentation.mappers.ConversationMapper;
import com.example.zokalocabackend.features.chat.presentation.mappers.MessageMapper;
import com.example.zokalocabackend.features.chat.presentation.requests.CreateConversationRequest;
import com.example.zokalocabackend.features.chat.presentation.requests.SendMessageRequest;
import com.example.zokalocabackend.features.chat.presentation.responses.GetConversationByIdResponse;
import com.example.zokalocabackend.features.chat.services.ConversationService;
import com.example.zokalocabackend.features.chat.services.MessageService;
import com.example.zokalocabackend.features.usermanagement.domain.Branch;
import com.example.zokalocabackend.features.usermanagement.domain.User;
import com.example.zokalocabackend.features.usermanagement.services.BranchService;
import com.example.zokalocabackend.features.usermanagement.services.UserBranchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/conversations")
public class ChatController {
    private final ConversationService conversationService;
    private final MessageService messageService;
    private final UserBranchService userBranchService;
    private final BranchService branchService;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/{conversationId}")
    public ResponseEntity<GetConversationByIdResponse> getConversationById(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String conversationId) {
        User loggedInUser = (User) userDetails;
        List<Branch> loggedInUserBranches = userBranchService.getAllBranchesByUserId(loggedInUser.getId());
        Conversation conversation = conversationService.getConversationById(conversationId);

        if (isBranchOfUserExcluded(loggedInUserBranches, conversation.getBranchIds().stream().toList())) {
            throw new AccessDeniedException("You are not allowed to view this conversation");
        }

        List<Branch> branches = branchService.getBranchesWithIds(conversation.getBranchIds().stream().toList());

        return ResponseEntity.ok(ConversationMapper.toGetConversationByIdResponse(conversation, branches));
    }

    @GetMapping("/branch/{branchId}")
    public ResponseEntity<List<ConversationItemDTO>> getAllConversationsByBranchId(@PathVariable String branchId) {
        List<Conversation> conversations = conversationService.getAllConversationsByBranchId(branchId);
        List<ConversationItemDTO> conversationDTOs = new ArrayList<>();

        for (Conversation conversation : conversations) {
            Message lastMessage = messageService.getLatestMessageOfConversation(conversation.getId()).orElse(null);
            List<Branch> branches = branchService.getBranchesWithIds(conversation.getBranchIds().stream().toList());
            conversationDTOs.add(ConversationMapper.toConversationItemDTO(conversation, lastMessage, branches));
        }

        return ResponseEntity.ok(conversationDTOs);
    }

    @GetMapping("/{conversationId}/messages")
    public ResponseEntity<List<MessageDTO>> getMessagesByConversationId(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String conversationId) {
        User loggedInUser = (User) userDetails;
        List<Branch> loggedInUserBranches = userBranchService.getAllBranchesByUserId(loggedInUser.getId());
        Conversation conversation = conversationService.getConversationById(conversationId);

        if (isBranchOfUserExcluded(loggedInUserBranches, conversation.getBranchIds().stream().toList())) {
            throw new AccessDeniedException("You are not allowed to view messages of this conversation");
        }

        List<MessageDTO> messageDTOs = new ArrayList<>();

        for (Message message : messageService.getAllMessagesByConversationId(conversationId)) {
            messageDTOs.add(MessageMapper.toMessageDTO(message));
        }

        return ResponseEntity.ok(messageDTOs);
    }

    @PostMapping()
    public ResponseEntity<?> createConversation(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody CreateConversationRequest request) {
        User loggedInUser = (User) userDetails;
        List<Branch> loggedInUserBranches = userBranchService.getAllBranchesByUserId(loggedInUser.getId());

        if (isBranchOfUserExcluded(loggedInUserBranches, request.branchIds().stream().toList())) {
            throw new IllegalArgumentException("One of your branches must be included in the conversation");
        }

        conversationService.createConversation(ConversationMapper.toDomain(request));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{conversationId}/messages")
    public ResponseEntity<?> sendMessage(@AuthenticationPrincipal UserDetails userDetails, @PathVariable String conversationId, @Valid @RequestBody SendMessageRequest sendMessageRequest) {
        User loggedInUser = (User) userDetails;
        List<Branch> loggedInUserBranches = userBranchService.getAllBranchesByUserId(loggedInUser.getId());
        Conversation conversation = conversationService.getConversationById(conversationId);

        if (isBranchOfUserExcluded(loggedInUserBranches, conversation.getBranchIds().stream().toList())) {
            throw new AccessDeniedException("You are not allowed to send message to this conversation");
        }

        Message message = MessageMapper.toDomain(sendMessageRequest, conversationId, loggedInUser);
        Message savedMessage = messageService.saveMessage(message);
        messagingTemplate.convertAndSend("/topic/conversations/" + message.getConversationId(), MessageMapper.toMessageDTO(savedMessage));

        return ResponseEntity.ok().build();
    }

    private boolean isBranchOfUserExcluded(List<Branch> loggedInUserBranches, List<String> branchIds) {
        boolean isBranchOfUserExcluded = true;

        for (Branch branch : loggedInUserBranches) {
            if (branchIds.contains(branch.getId())) {
                isBranchOfUserExcluded = false;
                break;
            }
        }

        return isBranchOfUserExcluded;
    }
}