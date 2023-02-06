package com.project.mypocketpurse.service;

import com.project.mypocketpurse.dto.ActionDTO;
import com.project.mypocketpurse.dto.ActionSearchDTO;
import com.project.mypocketpurse.model.Account;
import com.project.mypocketpurse.model.Action;
import com.project.mypocketpurse.model.ActionType;
import com.project.mypocketpurse.model.User;
import com.project.mypocketpurse.repository.AccountRepository;
import com.project.mypocketpurse.repository.ActionRepository;
import com.project.mypocketpurse.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActionService
        extends GenericService<Action, ActionDTO> {
    private final ActionRepository actionRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;

    public ActionService(ActionRepository actionRepository,
                         UserRepository userRepository,
                         AccountRepository accountRepository) {
        this.actionRepository = actionRepository;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
    }


    @Override
    public Action update(Action object) {
        return actionRepository.save(object);
    }

    @Override
    public Action updateFromDTO(ActionDTO object, Long id) {
        Action action = actionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Action with such ID: " + id + " not found"));
        action.setAmount(object.getAmount());
        action.setActionType(object.getActionType());
//        action.setDateTime(LocalDate.parse(object.getDateTime()));
        action.setAccount(object.getAccount());
        action.setLabel(object.getLabel());
        action.setCategory(object.getCategory());
        // И изменить просто так не можем
        return actionRepository.save(action);
    }

    @Transactional
    @Override
    public Action createFromDTO(ActionDTO newDtoObject) {
        Action newAction = new Action();
        Account account;
        if (newDtoObject.getAccount() == null) {
            return null;
        } else {
            account = accountRepository.findById(newDtoObject.getAccount().getId()).get();
        }
        User user = userRepository.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        newAction.setAmount(newDtoObject.getAmount());
        newAction.setActionType(newDtoObject.getActionType());
        newAction.setDateTime(LocalDate.parse(newDtoObject.getDateTime()));
        newAction.setCreatedBy(user.getLogin());
        newAction.setCreatedWhen(LocalDateTime.now());
        newAction.setAccount(account);
        if (newDtoObject.getLabel() != null) {
            newAction.setLabel(newDtoObject.getLabel());
        }
        newAction.setCategory(newDtoObject.getCategory());
        // Просто так добавить не можем
        if (newDtoObject.getActionType().equals(ActionType.EXPENSE)) {
            accountRepository.decreaseAmountByAction(newAction.getAmount(), account.getId());
        } else {
            accountRepository.increaseAmountByAction(newAction.getAmount(), account.getId());
        }
        return actionRepository.save(newAction);
    }

    @Override
    public Action createFromEntity(Action newObject) {
        return actionRepository.save(newObject);
    }

    @Transactional
    @Override
    public void delete(Long objectId) {
        Action action = actionRepository.findById(objectId)
                .orElseThrow(() -> new NotFoundException("Action with such ID: " + objectId + " not found"));
        // Просто так удалить не сможем..
        if (action.getActionType().equals(ActionType.EXPENSE)) {
            System.out.println("Before " + action.getAmount() + " " + objectId + " " + action.getAccount().getId());
            accountRepository.increaseAmountByAction(action.getAmount(), action.getAccount().getId());
            System.out.println("After " + action.getAmount() + " " + objectId + " " + action.getAccount().getId());
        } else {
            System.out.println("Before " + action.getAmount() + " " + objectId + " " + action.getAccount().getId());
            accountRepository.decreaseAmountByAction(action.getAmount(), action.getAccount().getId());
            System.out.println("After " + action.getAmount() + " " + objectId + " " + action.getAccount().getId());
        }
        actionRepository.delete(action);
    }

    @Override
    public Action getOne(Long objectId) {
        return actionRepository.findById(objectId)
                .orElseThrow(() -> new NotFoundException("Action with such ID: " + objectId + " not found"));
    }

    @Override
    public List<Action> listAll() {
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("admin")) {
            return actionRepository.findAll();
        }
        User user = userRepository.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        return actionRepository.findByCreatedBy(user.getLogin());
    }

    /*
    TODO 1. Добавить ему поиск по пользователям
     */
    public Page<Action> searchActions(ActionSearchDTO actionSearchDTO,
                                      Pageable pageRequest) {
        String userId;
        String accountId = actionSearchDTO.getAccount() != null ? String.valueOf(actionSearchDTO.getAccount().getId()) : "%";
        String categoryId = actionSearchDTO.getCategory() != null ? String.valueOf(actionSearchDTO.getCategory().getId()) : "%";
        String labelId = actionSearchDTO.getLabel() != null ? String.valueOf(actionSearchDTO.getLabel().getId()) : "%";
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("admin")) {
            userId = "%";
//            userId = actionSearchDTO.getUser() != null ? String.valueOf(actionSearchDTO.getUser().getId()) : "%";
//            userId = actionSearchDTO.getLogin();
            Page<Action> actions = actionRepository.searchAction(accountId, categoryId, labelId, userId, pageRequest);
            return new PageImpl<>(actions.getContent(), pageRequest, actions.getTotalElements());
        } else {
            User user = userRepository.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
            userId = String.valueOf(user.getId());
        }
        Page<Action> actions = actionRepository.searchAction(accountId, categoryId, labelId, userId, pageRequest);
        return new PageImpl<>(actions.getContent(), pageRequest, actions.getTotalElements());
    }


    public Page<Action> getAllPaginated(Pageable pageRequest) {
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("admin")) {
            Page<Action> actions = actionRepository.findAll(pageRequest);
            return new PageImpl<>(actions.getContent(), pageRequest, actions.getTotalElements());
        }
        User user = userRepository.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        Page<Action> actions = actionRepository.findByCreatedBy(user.getLogin(), pageRequest);
        return new PageImpl<>(actions.getContent(), pageRequest, actions.getTotalElements());
    }
}
