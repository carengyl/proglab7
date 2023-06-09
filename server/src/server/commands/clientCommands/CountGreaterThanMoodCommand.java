package server.commands.clientCommands;

import common.util.udp.Request;
import common.util.udp.Response;
import common.commands.AbstractCommand;
import common.commands.ArgumentValidationFunctions;
import common.commands.CommandArgument;
import common.commands.CommandData;
import common.util.Validators;
import common.entities.CollectionManager;
import common.entities.Mood;
import common.exceptions.InvalidNumberOfArgsException;
import common.exceptions.NoUserInputException;
import common.exceptions.ValidationException;
import server.util.CommandProcessor;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

public class CountGreaterThanMoodCommand extends AbstractCommand implements Serializable {
    private final CollectionManager collection;
    public CountGreaterThanMoodCommand(CommandProcessor commandProcessor) {
        super("count_greater_than_mood", "Count collection elements which mood is greater than @mood",1, "@mood from Mood enum",
                ArgumentValidationFunctions.VALIDATE_MOOD.getValidationFunction());
        this.collection = commandProcessor.getCollectionManager();
    }

    @Override
    public Optional<Response> executeCommand(Request request) throws NoUserInputException {
        Mood mood = Mood.getMoodByNumber(request.getCommandArgument().getEnumNumber());
        int greaterMoods = 0;
        for (long key: collection.getHumanBeings().keySet()) {
            if (Objects.requireNonNull(mood).compareTo(collection.getHumanBeings().get(key).getMood()) < 0) {
                greaterMoods++;
            }
        }
        return Optional.of(new Response("People with Mood greater than " + mood + ":" + greaterMoods));
    }

    @Override
    public CommandArgument validateArguments(CommandArgument arguments, CommandData commandData) throws ValidationException, InvalidNumberOfArgsException {
        Validators.validateNumberOfArgs(arguments.getNumberOfArgs(), commandData.numberOfArgs());
        int moodNumber = Validators.validateArg(arg -> ((int) arg < Mood.values().length + 1) && ((int) arg > 0),
                "Pick a Mood number:\n" + Mood.show(),
                Integer::parseInt,
                arguments.getArg());

        arguments.setEnumNumber(moodNumber);
        return arguments;
    }
}
