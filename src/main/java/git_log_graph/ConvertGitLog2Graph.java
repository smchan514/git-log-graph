package git_log_graph;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;

import git_log_graph.git_log.GitLogParser;
import git_log_graph.git_log.data.GitCommit;

public class ConvertGitLog2Graph implements ApplicationListener<ContextStartedEvent> {
    private static final org.apache.logging.log4j.Logger LOGGER = org.apache.logging.log4j.LogManager
            .getLogger(ConvertGitLog2Graph.class);

    private String _infile;
    private String _outfile;

    private GitLogParser _parser;
    private IGraphGenerator _generator;

    public ConvertGitLog2Graph() {
        // ...
    }

    @Required
    public void setInfile(String infile) {
        _infile = infile;
        _outfile = infile;
    }

    public void setOutfile(String outfile) {
        _outfile = outfile;
    }

    @Required
    public void setParser(GitLogParser parser) {
        _parser = parser;
    }

    @Required
    public void setGenerator(IGraphGenerator generator) {
        _generator = generator;
    }

    @Override
    public void onApplicationEvent(ContextStartedEvent event) {
        try {
            List<GitCommit> list = _parser.parseGitLog(_infile);
            LOGGER.debug("Got " + list.size() + " commits");

            // Generate graph
            _generator.generate(_outfile, list);
        } catch (IOException e) {
            LOGGER.error("Failed to run", e);
        }
    }

}
