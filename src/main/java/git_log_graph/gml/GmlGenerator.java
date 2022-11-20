package git_log_graph.gml;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import git_log_graph.IGraphGenerator;
import git_log_graph.git_log.data.GitCommit;
import gml.data.GmlEdge;
import gml.data.GmlGraph;
import gml.data.GmlLabelGraphics;
import gml.data.GmlNode;
import gml.data.GmlNodeGraphics;
import gml.io.GmlWriter;

public class GmlGenerator implements IGraphGenerator {
    private static final org.apache.logging.log4j.Logger LOGGER = org.apache.logging.log4j.LogManager
            .getLogger(GmlGenerator.class);
    private static final String OUTFILE_SUFFIX = ".gml";

    public GmlGenerator() {
        // ...
    }

    @Override
    public void generate(String path, List<GitCommit> list) throws IOException {
        GmlGraph graph = generateGraph(list);

        if (!path.endsWith(OUTFILE_SUFFIX)) {
            path += OUTFILE_SUFFIX;
        }

        LOGGER.info("Writing to " + path);
        GmlWriter gw = new GmlWriter();
        gw.writeGml(path, graph);
    }

    private GmlGraph generateGraph(List<GitCommit> list) {
        GmlGraph graph = new GmlGraph();
        HashMap<String, GmlNode> mapHash2Node = new HashMap<>();

        // First pass: insert nodes
        for (GitCommit commit : list) {
            GmlNode node = graph.createNode();
            String thisCommit = commit.getCommit();
            node.setLabel(thisCommit);
            mapHash2Node.put(thisCommit, node);

            // Construct message
            StringBuilder sb = new StringBuilder();
            sb.append(commit.getCommit());
            sb.append("\n");
            sb.append(commit.getAuthor());
            sb.append("\n");
            sb.append(commit.getDate());
            sb.append("\n\n");
            sb.append(commit.getMessage());
            GmlLabelGraphics lg = node.createLabelGraphics();
            lg.setText(sb.toString());
            lg.setAlignment("left");

            // Color a PR merge node red
            if (commit.getMessage().startsWith("Merged PR")) {
                GmlNodeGraphics gr = node.createNodeGraphics();
                gr.setFillColor("#FF0000");
                gr.setType(GmlNodeGraphics.TYPE_TRAPEZOID2);
            }
        }

        // Second pass: insert edges
        for (GitCommit commit : list) {
            String thisCommit = commit.getCommit();

            List<String> parents = commit.getParents();
            for (String srcCommit : parents) {
                GmlEdge edge = graph.createEdge();

                edge.setSourceNode(mapHash2Node.get(srcCommit));
                edge.setTargetNode(mapHash2Node.get(thisCommit));
            }
        }

        return graph;
    }

}
