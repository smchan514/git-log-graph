package git_log_graph.git_log;

enum EnumParserState {
    WAITING_FOR_NEW_COMMIT,
    WAITING_FOR_MESSAGE,
    READING_MESSAGE,
    READING_GPGSIG
}
