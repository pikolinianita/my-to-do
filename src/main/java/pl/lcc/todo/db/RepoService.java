package pl.lcc.todo.db;

import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lcc.todo.entities.EventDTO;
import pl.lcc.todo.entities.EventEntity;
import pl.lcc.todo.entities.EventReq;
import pl.lcc.todo.entities.ProjectDTO;
import pl.lcc.todo.entities.ProjectEntity;
import pl.lcc.todo.entities.ProjectReq;
import pl.lcc.todo.entities.TagEntity;
import pl.lcc.todo.entities.UserDTO;
import pl.lcc.todo.entities.UserEntity;
import pl.lcc.todo.entities.UserReq;

/**
 *
 * @author piko
 */
@Slf4j
@Service
public class RepoService {

    ProjectRepository projectRepo;

    TagRepository tagRepo;

    RewardRepository rewRepo;

    UserRepository userRepo;

    EventRepository eventRepo;

    public RepoService(ProjectRepository projectRepo, TagRepository tagRepo, RewardRepository rewRepo, UserRepository userRepo, EventRepository eventRepo) {
        this.projectRepo = projectRepo;
        this.tagRepo = tagRepo;
        this.rewRepo = rewRepo;
        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
        log.info("repoServ constructor");
    }

    @Transactional
    public Optional<ProjectEntity> createProject(long userId, ProjectReq source) {

        var owner = userRepo.findById(userId).orElseThrow();
        if (canCreateProject(userId, source.name())) {
            return saveNewProject(owner, source);
        } else {
            log.info("Project Existed!!!");
            return Optional.empty();
        }
    }

    private Optional<ProjectEntity> saveNewProject(UserEntity owner, ProjectReq source) {

        var entity = new ProjectEntity(source);
        var tags = source.tags().stream()
                .map(name -> {
                    return tagRepo.findByName(name).orElse(new TagEntity(name));
                })
                .collect(Collectors.toSet());
        entity.setTags(tags);
        owner.addProject(entity);
        return Optional.of(entity);
    }

    private boolean canCreateProject(long userId, String name) {
        return projectRepo.findByOwner_IdAndName(userId, name).isEmpty();
    }

    @Transactional
    public Optional<UserEntity> createUser(UserReq source) {

        log.info("number of users" + userRepo.count());
        if (userRepo.findByName(source.name()).isEmpty()) {
            return Optional.of(userRepo.save(new UserEntity(source)));
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<EventEntity> createEvent(long userId, long projectId, EventReq req) {

        log.info("create Event");
        var event = new EventEntity(req);
        projectRepo.findByOwner_IdAndId(userId, projectId).orElseThrow().addEvent(event);
        return Optional.of(event);
    }

    @Transactional
    public Optional<EventEntity> deleteEvent(long userId, long projectId, long eventId) {
        var project = projectRepo.findById(projectId).orElseThrow();
        var event = eventRepo.findById(eventId).orElseThrow(); 
        project.removeEvent(event);      
        return Optional.of(event);
    }

    public boolean removeUser(long userId) {
        userRepo.deleteById(userId);
        return true;
    }

    @Transactional
    public boolean removeProject(long userId, long projectID) {
        var user = userRepo.findById(userId).orElseThrow().removeProject(projectID);
        log.info("deleted project from user: " + user.getName());
        return true;
    }

    public Optional<ProjectEntity> getProjectByName(long userId, String name) {
        return projectRepo.findByOwner_IdAndName(userId, name);
    }

    public Optional<UserEntity> findUser(String name) {
        return userRepo.findByName(name);
    }

    public Optional<UserDTO> findUserWithProjects(long id) {
        return userRepo.findById(id).map(UserDTO::new);
    }

    @Transactional
    public Optional<ProjectDTO> getProject(long userId, long projectId) {
        return projectRepo.findByOwner_IdAndId(userId, projectId).map(ProjectDTO::new);
    }

    @Transactional
    public Optional<EventDTO> getEvent(long userId, long eventId) {
        return eventRepo.findEventByProject_Owner_IdAndId(userId, eventId).map(EventDTO::new);
    }

    public Optional<UserDTO> findUserWithProjects(String name) {
        return findUser(name).map(UserDTO::new);
    }

}
