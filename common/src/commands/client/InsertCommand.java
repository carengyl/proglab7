package commands.client;

import UDPutil.Response;
import commands.AbstractCommand;
import commands.CommandArgument;
import commonUtil.Validators;
import entities.CollectionOfHumanBeings;
import exceptions.InvalidNumberOfArgsException;
import exceptions.NoUserInputException;
import exceptions.ValidationException;

import java.io.Serializable;
import java.util.Optional;

public class InsertCommand extends AbstractCommand implements Serializable {
    private final CollectionOfHumanBeings collection;

    public InsertCommand(CollectionOfHumanBeings collection) {
        super("insert", "Add element to collection by @key", 1, "@key - unique long of element");
        this.collection = collection;
        this.setNeedsComplexData(true);
    }

    @Override
    public Optional<Response> executeCommand(CommandArgument argument) throws NoUserInputException {
        long key = argument.getLongArg();
        collection.addByKey(key, argument.getHumanBeingArgument());
        return Optional.of(new Response("Added Human Being by key: " + key,
                argument.getHumanBeingArgument()));
    }

    @Override
    public CommandArgument validateArguments(CommandArgument arguments) throws ValidationException, InvalidNumberOfArgsException {
        Validators.validateNumberOfArgs(arguments.getNumberOfArgs(), this.getNumberOfArgs());
        long key = Validators.validateArg(arg -> (!collection.getHumanBeings().containsKey((long) arg)),
                "Key isn't unique",
                Long::parseLong,
                arguments.getArg());
        arguments.setLongArg(key);
        return arguments;
    }
}
