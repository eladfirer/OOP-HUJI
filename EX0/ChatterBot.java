import java.util.*;

/**
 * Base file for the ChatterBot exercise.
 * The bot's replyTo method receives a statement.
 * If it starts with the constant REQUEST_PREFIX, the bot returns
 * whatever is after this prefix. Otherwise, it returns one of
 * a few possible replies as supplied to it via its constructor.
 * In this case, it may also include the statement after
 * the selected reply (coin toss).
 *
 * @author Dan Nirel
 */
class ChatterBot {
    static final String REQUEST_PREFIX = "say ";
    static final String ECHO_PREFIX = "echo ";
    static final String PLACEHOLDER_FOR_REQUESTED_PHRASE = "<phrase>";
    static final String PLACEHOLDER_FOR_ILLEGAL_REQUEST = "<request>";


    Random rand = new Random();
    String name;
    String[] legalRequestsReplies;
    String[] illegalRequestsReplies;

    /**
     * This is a constructor for the class ChatterBot
     *
     * @param name                   - name of the bot
     * @param legalRequestsReplies   - patterns of legal replies for bot
     * @param illegalRequestsReplies - patterns of illegal replies for bot
     */
    ChatterBot(String name, String[] legalRequestsReplies, String[] illegalRequestsReplies) {
        this.name = name;
        this.illegalRequestsReplies = new String[illegalRequestsReplies.length];
        this.legalRequestsReplies = new String[legalRequestsReplies.length];

        for (int i = 0; i < illegalRequestsReplies.length; i = i + 1) {
            this.illegalRequestsReplies[i] = illegalRequestsReplies[i];
        }

        for (int i = 0; i < legalRequestsReplies.length; i = i + 1) {
            this.legalRequestsReplies[i] = legalRequestsReplies[i];
        }
    }

    /**
     * This method controls the reply system of the bot
     */
    String replyTo(String statement) {
        String phrase;
        if (statement.startsWith(REQUEST_PREFIX)) {
            // the bot will generate a special Legal response using statement
            phrase = statement.replaceFirst(REQUEST_PREFIX, "");
            return replyToLegalRequest(phrase);
        }

        if (statement.startsWith(ECHO_PREFIX)) {
            // we don’t repeat the echo prefix, so delete it from the reply
            return statement.replaceFirst(ECHO_PREFIX, "");
        }
        // the bot will generate a special Illegal response using statement
        return replyToIllegalRequest(statement);
    }

    /**
     * This method replace the placeholder with replacement in a random pattern
     */
    String replacePlaceholderInARandomPattern(String[] patterns, String placeholder, String replacement) {
        int randomIndex = rand.nextInt(patterns.length);
        String responsePattern = patterns[randomIndex];
        String reply = responsePattern.replaceAll(placeholder, replacement);
        return reply;
    }

    /**
     * This method creates a reply for legal requests
     */
    String replyToLegalRequest(String statement) {
        return replacePlaceholderInARandomPattern(this.legalRequestsReplies, PLACEHOLDER_FOR_REQUESTED_PHRASE,
                statement);
    }

    /**
     * This method creates a reply for illegal requests
     */
    String replyToIllegalRequest(String statement) {
        return replacePlaceholderInARandomPattern(this.illegalRequestsReplies,
                PLACEHOLDER_FOR_ILLEGAL_REQUEST, statement);
    }

    String getName() {
        return name;
    }
}
