package commands.clientCommands;

import UDPutil.Response;
import commands.AbstractCommand;
import commands.CommandArgument;
import commands.CommandData;
import commonUtil.HumanBeingFactory;
import commonUtil.Validators;
import entities.CollectionManager;
import exceptions.InvalidNumberOfArgsException;
import exceptions.NoUserInputException;
import exceptions.ValidationException;

import java.io.Serializable;
import java.util.Optional;

public class UpdateCommand extends AbstractCommand implements Serializable {
    private final CollectionManager collection;

    public UpdateCommand(CollectionManager collection) {
        super("update", "Update element by @id", 1, "@id - (long) id of collection element");
        this.collection = collection;
    }

    @Override
    public Optional<Response> executeCommand(CommandArgument argument) throws NoUserInputException {
        long id = argument.getLongArg();
        if (collection.checkForId(id)) {
            if (argument.getHumanBeingArgument() != null) {
                collection.updateById(id, argument.getHumanBeingArgument());
                return Optional.of(new Response("Updated human being by id: " + argument.getLongArg()));
            } else if (argument.getElementArgument() != null) {
                try {
                    HumanBeingFactory humanBeingFactory = new HumanBeingFactory(id);
                    humanBeingFactory.setVariables(argument.getElementArgument());
                    collection.updateById(id, humanBeingFactory.getCreatedHumanBeing());
                    return Optional.of(new Response("Updated human being by id: " + argument.getLongArg()));
                }
                catch (ValidationException e) {
                    return Optional.of(new Response(e.getMessage()));
                }
            } else {
                return Optional.of(new Response(this.getCommandData(), argument));
            }
        } else {
            return Optional.of(new Response("There is no Human Being with id: " + id));
        }
    }

    @Override
    public CommandArgument validateArguments(CommandArgument arguments, CommandData commandData) throws ValidationException, InvalidNumberOfArgsException {
        Validators.validateNumberOfArgs(arguments.getNumberOfArgs(), commandData.numberOfArgs());
        long id = Validators.validateArg(arg -> true,
                "There is no Human Being with id: " + arguments.getArg(),
                Long::parseLong,
                arguments.getArg());
        arguments.setLongArg(id);
        return arguments;
    }
}
