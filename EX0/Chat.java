import java.util.*;

class Chat {
    public static void main(String[] args) {

        // initializing the bots for converstiobn
        ChatterBot[] bots = new ChatterBot[2];
        bots[0] = new ChatterBot("Don", new String[]{
                "say " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE + "? okay: "
                + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE,
                "say " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE + " ? stop telling me to say "
                + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE,
                ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE + " " +
                ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE + "" +
                ", are you satisfied?"}
                , new String[]{"what is " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST
                + "? give me a normal request"
                , "say i should say " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST,
                "I cant understand what to do when you say "
                + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST + "!!",
                "stop!", "say something useful and change the subject"
                , "echo Don is the king"});

        bots[1] = new ChatterBot("Julio", new String[]{
                "i like saying " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE,
                ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE + " " +
                ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE + " "
                + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE + ", what a wonderful word",
                "say " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE + ", bon appetit"
                }, new String[]{"say say " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST,
                "whaaat I'm confused when you say " +
                ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST
                , "please let me know what to say, i dont understand what is " +
                ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST
                , "lets change a subject this is too much", "pls stop", "echo the king is talking"});


        // starting word for conversation
        String statement = "say something";
        System.out.println(statement);


        // looping the conversation
        while (true) {
            for (ChatterBot bot : bots) {
                statement = bot.replyTo(statement);
                System.out.println(bot.getName() + ": " + statement);
            }
        }
    }
}