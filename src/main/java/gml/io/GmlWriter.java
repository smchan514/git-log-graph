package gml.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import gml.data.GmlEdge;
import gml.data.GmlGraph;
import gml.data.GmlLabelGraphics;
import gml.data.GmlNode;
import gml.data.GmlNodeGraphics;

public class GmlWriter {
	private static final org.apache.logging.log4j.Logger LOGGER = org.apache.logging.log4j.LogManager
			.getLogger(GmlWriter.class);

	public GmlWriter() {
		// ...
	}

    public void writeGml(String path, GmlGraph graph) throws IOException {
        LOGGER.debug("Writing to " + path);
		try (FileWriter fw = new FileWriter(path)) {
			try (BufferedWriter bw = new BufferedWriter(fw)) {

				appendLine(bw, "graph [");
				appendLine(bw, "  version " + graph.getVersion());
				appendLine(bw, "  directed " + graph.getDirected());

				writeNodes(bw, graph);
				writeEdges(bw, graph);

				appendLine(bw, "]");
			}
		}
	}

	private void writeNodes(BufferedWriter bw, GmlGraph graph) throws IOException {
		for (GmlNode node : graph.getNodes()) {
			appendLine(bw, "node [");
			appendLine(bw, "  id " + node.getNodeId());
			appendLine(bw, "  label \"" + escapeGmlString(node.getLabel()) + "\"");

			// Name is not shown in yEd
//			appendLine(bw, "  name \"" + node.getName() + "\"");

			GmlNodeGraphics gr = node.getGraphics();
			if (gr != null) {
				appendLine(bw, "graphics [");

				if (gr.getFillColor() != null) {
					appendLine(bw, "    fill \"" + gr.getFillColor() + "\"");
				}

				if (gr.getType() != null) {
					appendLine(bw, "    type \"" + gr.getType() + "\"");
				}

				appendLine(bw, "]");
			}

			GmlLabelGraphics lg = node.getLabelGraphics();
			if (lg != null) {
				appendLine(bw, "LabelGraphics [");
                if (lg.getText() != null) {
                    appendLine(bw, "  text \"" + escapeGmlString(lg.getText()) + "\"");
                }

                if (lg.getAlignment() != null) {
                    appendLine(bw, "  alignment \"" + lg.getAlignment() + "\"");
                }

                if (lg.getFontSize() != null) {
                    appendLine(bw, "  fontSize " + lg.getFontSize());
                }

                if (lg.getFontStyle() != null) {
                    appendLine(bw, "  fontStyle \"" + lg.getFontStyle() + "\"");
                }

                if (lg.getFontName() != null) {
                    appendLine(bw, "  fontName \"" + lg.getFontName() + "\"");
                }
				appendLine(bw, "]");
			}

            if (node.isGroup()) {
                appendLine(bw, "isGroup 1");
            }

            if (node.getGroupNode() != null) {
                appendLine(bw, "gid " + node.getGroupNode().getNodeId());
            }

			appendLine(bw, "]");
			bw.newLine();
		}
	}

	private void writeEdges(BufferedWriter bw, GmlGraph graph) throws IOException {
		for (GmlEdge edge : graph.getEdges()) {
			appendLine(bw, "edge [");
			appendLine(bw, "  id " + edge.getEdgeId());
			appendLine(bw, "  source " + edge.getSourceNode().getNodeId());
			appendLine(bw, "  target " + edge.getTargetNode().getNodeId());
			appendLine(bw, "]");
		}
	}

	private void appendLine(BufferedWriter bw, String string) throws IOException {
		bw.append(string);
		bw.newLine();
	}

	private String escapeGmlString(String str) {
        if (str == null)
            return "null";
        str = str.replace("&", "&amp;");
		str = str.replace("\"", "&quot;");
		return str;
	}

}
