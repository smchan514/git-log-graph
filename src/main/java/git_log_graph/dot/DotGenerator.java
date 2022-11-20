package git_log_graph.dot;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import git_log_graph.IGraphGenerator;
import git_log_graph.dot.data.DotArc;
import git_log_graph.dot.data.DotNode;
import git_log_graph.git_log.data.GitCommit;

public class DotGenerator implements IGraphGenerator {

    public DotGenerator() {
        // ...
    }

    @Override
    public void generate(String path, List<GitCommit> list) throws IOException {
        LinkedList<DotNode> nodes = new LinkedList<DotNode>();
        LinkedList<DotArc> arcs = new LinkedList<DotArc>();

        for (GitCommit commit : list) {
            String thisCommit = commit.getCommit();
            nodes.add(new DotNode(thisCommit, thisCommit));

            List<String> parents = commit.getParents();
            for (String srcCommit : parents) {
                DotArc arc = new DotArc(srcCommit, thisCommit);
                arcs.add(arc);
            }
        }

        try (FileWriter fw = new FileWriter(path)) {
            try (BufferedWriter bw = new BufferedWriter(fw)) {

                bw.append("digraph git {");
                bw.newLine();

                for (DotNode dotNode : nodes) {
                    bw.append("  \"" + dotNode.getNodeId() + "\" [label=\"" + dotNode.getLabel() + "\"]");
                    bw.newLine();
                }

                for (DotArc dotArc : arcs) {
                    bw.append("  \"" + dotArc.getSourceNodeId() + "\" -> \"" + dotArc.getTargetNodeId() + "\" ");
                    bw.newLine();
                }

                bw.append("}");
                bw.newLine();
            }
        }

    }

}
