package git_log_graph;

import java.io.IOException;
import java.util.List;

import git_log_graph.git_log.data.GitCommit;

public interface IGraphGenerator {
    public void generate(String path, List<GitCommit> list) throws IOException;
}
