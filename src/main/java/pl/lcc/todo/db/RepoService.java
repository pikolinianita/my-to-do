package pl.lcc.todo.db;

import java.util.Optional;
import org.springframework.stereotype.Service;
import pl.lcc.todo.entities.ProjectEntity;
import pl.lcc.todo.entities.ProjectReq;

/**
 *
 * @author piko
 */
@Service
public class RepoService {

    ProjectRepository projectRepo;

    public RepoService(ProjectRepository projectRepo) {
        this.projectRepo = projectRepo;
        System.out.println("repoServ constructor");
    }

    public boolean createProject(ProjectReq source) {

        System.out.println("number of projects" + projectRepo.count());
        if (projectRepo.findByName(source.name()).isEmpty()) {
            projectRepo.save(new ProjectEntity(source));
            return true;
        } else {
            return false;
        }
    }

    public Optional<ProjectEntity> getProjectByName(String name){
       return projectRepo.findByName(name);
    }
    
    
}
