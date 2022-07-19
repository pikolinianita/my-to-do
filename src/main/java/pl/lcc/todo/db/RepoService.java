package pl.lcc.todo.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.lcc.todo.entities.ProjectDTO;
import pl.lcc.todo.entities.ProjectEntity;
import pl.lcc.todo.entities.ProjectReq;
import pl.lcc.todo.entities.RewardEntity;
import pl.lcc.todo.entities.TagEntity;

/**
 *
 * @author piko
 */
@Service
public class RepoService {

    ProjectRepository projectRepo;

    TagRepository tagRepo;

    RewardRepository rewRepo;

    public RepoService(ProjectRepository projectRepo, TagRepository tagRepo, RewardRepository rewRepo) {
        this.projectRepo = projectRepo;
        this.tagRepo = tagRepo;
        this.rewRepo = rewRepo;
        System.out.println("repoServ constructor");
    }

    @Transactional
    public boolean createProject(ProjectReq source) {

        System.out.println("number of projects" + projectRepo.count());
        if (projectRepo.findByName(source.name()).isEmpty()) {
            saveNewProject(source);
            return true;
        } else {
            return false;
        }
    }

    private void saveNewProject(ProjectReq source) {
        var entity = new ProjectEntity(source);
        var tags = source.tags().stream()
                .map(name -> {
                    return tagRepo.findByName(name).orElse(new TagEntity(name));
                })
                .collect(Collectors.toSet());
        entity.setTags(tags);
        projectRepo.save(entity);
    }

    public Optional<ProjectDTO> getProjectByName(String name) {
        return projectRepo.findByName(name).map(ProjectDTO::new);
    }

    public List<RewardEntity> getAllRewards() {
        return rewRepo.findAll();
    }

    public boolean addReward(String name) {
        if (rewRepo.findByName(name).isEmpty()) {
            rewRepo.save(new RewardEntity(name));
            return true;
        } else {
            return false;
        }
    }

//    public boolean addRewardToProject(long project, long reward){
//       var proj = projectRepo.findById(project).orElseThrow();
//       var rew = rewRepo.findById(reward).orElseThrow().getId();
//       proj.setReward(reward);
//       return true;
//    }
    
}
