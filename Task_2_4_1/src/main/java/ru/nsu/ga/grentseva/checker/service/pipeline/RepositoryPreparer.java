package ru.nsu.ga.grentseva.checker.service.pipeline;

import ru.nsu.ga.grentseva.checker.model.CourseConfig;
import ru.nsu.ga.grentseva.checker.model.Group;
import ru.nsu.ga.grentseva.checker.model.Student;
import ru.nsu.ga.grentseva.checker.service.git.GitService;
import ru.nsu.ga.grentseva.checker.service.logging.Logger;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class RepositoryPreparer {

    private final GitService gitService = new GitService();
    private final Logger logger = new Logger();

    public Set<String> prepare(CourseConfig config, File workspace) {
        Set<String> preparedRepositories = new HashSet<>();

        for (Group group : config.getGroups()) {
            for (Student student : group.getStudents()) {
                File repositoryDirectory = new File(workspace, student.getGithubId());

                try {
                    gitService.cloneOrUpdate(student.getRepositoryUrl(), repositoryDirectory);
                    logger.repositoryPrepared(student.getGithubId());
                    preparedRepositories.add(student.getGithubId());
                } catch (Exception e) {
                    logger.gitError(student.getGithubId());
                }
            }
        }

        return preparedRepositories;
    }
}