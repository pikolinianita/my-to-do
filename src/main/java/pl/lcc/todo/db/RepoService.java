package pl.lcc.todo.db;

import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    public RepoService(ProjectRepository projectRepo, TagRepository tagRepo, RewardRepository rewRepo, UserRepository userRepo) {
        this.projectRepo = projectRepo;
        this.tagRepo = tagRepo;
        this.rewRepo = rewRepo;
        this.userRepo = userRepo;
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

    public Optional<ProjectEntity> getProjectByName(long userId, String name) {
        return projectRepo.findByOwner_IdAndName(userId, name);
    }

    public Optional<ProjectDTO> getProject(long userId, long projectId){
       return projectRepo.findById(projectId).map(ProjectDTO::new);
    }
    
    public Optional<UserEntity> findUser(String name) {
        return userRepo.findByName(name);
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
    public Optional<EventEntity> createEvent(long userId, long projectId, EventReq req){
        
        log.info("create Event");
        var event = new EventEntity(req);       
        projectRepo.findById(projectId).orElseThrow().addEvent(event);
        return Optional.of(event);
    }
    
    @Transactional
    public Optional<EventEntity> deleteEvent(long userId, long projectId, long eventId){
       var project = projectRepo.findById(projectId).orElseThrow();
       var event = eventRepo.findById(userId).orElseThrow();
       project.removeEvent(event);
       return Optional.of(event);
    }
    
    
    public boolean removeUser(long userId){
        userRepo.deleteById(userId);
        return true;
    }
    
    @Transactional
    public boolean removeProject(long userId, long projectID){
        var user = userRepo.findById(userId).orElseThrow().removeProject(projectID);
        log.info("deleted user: " + user.getName());
        //projectRepo.deleteById(projectID);
        return true;
    }

    public Optional<UserDTO> getUserByName(String name) {
       var userOpt = userRepo.findByName(name);
       if(userOpt.isEmpty()){
        return Optional.empty();
       }
       else
       {
           var user = userOpt.get();
           return Optional.of(new UserDTO(user.getName(), user.getId(), user.getProjects()));
       }
    }
    
}
