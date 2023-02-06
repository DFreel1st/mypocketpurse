package com.project.mypocketpurse.service;

import com.project.mypocketpurse.dto.LabelDTO;
import com.project.mypocketpurse.model.Label;
import com.project.mypocketpurse.model.User;
import com.project.mypocketpurse.repository.LabelRepository;
import com.project.mypocketpurse.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LabelService
        extends GenericService<Label, LabelDTO> {

    private final LabelRepository labelRepository;
    private final UserRepository userRepository;

    public LabelService(LabelRepository labelRepository,
                        UserRepository userRepository) {
        this.labelRepository = labelRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Label update(Label object) {
        return labelRepository.save(object);
    }

    @Override
    public Label updateFromDTO(LabelDTO object, Long id) {
        Label label = labelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Label with such ID: " + id + " not found"));
        label.setName(object.getName());
        label.setDescription(object.getDescription());
        return labelRepository.save(label);
    }

    @Override
    public Label createFromDTO(LabelDTO newDtoObject) {
        Label newLabel = new Label();
        User user = userRepository.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        newLabel.setName(newDtoObject.getName());
        newLabel.setDescription(newDtoObject.getDescription());
        newLabel.setUser(user);
        newLabel.setCreatedBy(user.getLogin());
        newLabel.setCreatedWhen(LocalDateTime.now());
        return labelRepository.save(newLabel);
    }

    @Override
    public Label createFromEntity(Label newObject) {
        return labelRepository.save(newObject);
    }

    @Override
    public void delete(Long objectId) {
        Label label = labelRepository.findById(objectId)
                .orElseThrow(() -> new NotFoundException("Label with such ID: " + objectId + " not found"));
        labelRepository.delete(label);
    }

    @Override
    public Label getOne(Long objectId) {
        return labelRepository.findById(objectId)
                .orElseThrow(() -> new NotFoundException("Label with such ID: " + objectId + " not found"));
    }

    @Override
    public List<Label> listAll() {
        if (SecurityContextHolder.getContext().getAuthentication().getName().equals("admin")) {
            return labelRepository.findAll();
        }
        User user = userRepository.findUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());
        return labelRepository.findLabelByUserId(user.getId());
    }
}
