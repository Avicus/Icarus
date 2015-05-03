package net.avicus.icarus.module;

public class ParserException extends Exception {

    public ParserException(Parser parser, String error) {
        super("[" + parser.getClass().getSimpleName() + "] " + error);
    }

    public ParserException(Parser parser, String error, Exception ex) {
        super("[" + parser.getClass().getSimpleName() + "] " + error, ex);
    }

}
