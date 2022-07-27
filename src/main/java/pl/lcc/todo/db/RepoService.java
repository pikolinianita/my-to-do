package pl.lcc.todo.db;

import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lcc.todo.entities.EventEntity;
import pl.lcc.todo.entities.EventReq;
import pl.lcc.todo.entities.ProjectEntity;
import pl.lcc.todo.entities.ProjectReq;
import pl.lcc.todo.entities.TagEntity;
import pl.lcc.todo.entities.UserEntity;
import pl.lcc.todo.entities.UserReq;

/**
 *
 * @author piko
 */
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
        System.out.println("repoServ constructor");
    }

    @Transactional
    public Optional<ProjectEntity> createProject(long userId, ProjectReq source) {

        System.out.println("---number of projects" + projectRepo.count());
        var owner = userRepo.findById(userId).orElseThrow();
        if (canCreateProject(userId, source.name())) {
            System.out.println("---will create!!!");
            return saveNewProject(owner, source);
        } else {
            System.out.println("---Existed!!!");
            return Optional.empty();

        }
    }

    private Optional<ProjectEntity> saveNewProject(UserEntity owner, ProjectReq source) {
        
        var entity = new ProjectEntity(source);
        System.out.println("Owner: " + owner.toString() + "  project: " + entity.toString());
        var tags = source.tags().stream()
                .map(name -> {
                    return tagRepo.findByName(name).orElse(new TagEntity(name));
                })
                .collect(Collectors.toSet());
        entity.setTags(tags);
        owner.addProject(entity); 
        System.out.println("Owner: " + owner.toString() + "  project: " + entity.toString());
        return Optional.of(entity);
    }

    private boolean canCreateProject(long userId, String name) {
        return projectRepo.findByOwner_IdAndName(userId, name).isEmpty();
    }

    public Optional<ProjectEntity> getProjectByName(long userId, String name) {
        return projectRepo.findByOwner_IdAndName(userId, name);
    }

    public Optional<UserEntity> findUser(String name) {
        return userRepo.findByName(name);
    }

    @Transactional
    public Optional<UserEntity> createUser(UserReq source) {

        System.out.println("number of users" + userRepo.count());
        if (userRepo.findByName(source.name()).isEmpty()) {
            return Optional.of(userRepo.save(new UserEntity(source)));
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    public Optional<EventEntity> createEvent(long userId, long projectId, EventReq req){
        
        System.out.println("create");
        var event = new EventEntity(req);
        System.out.println(event);        
        projectRepo.findById(projectId).orElseThrow().addEvent(event);
        
        System.out.println("after repo");
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
        
        //projectRepo.deleteById(projectID);
        return true;
    }
    
}
