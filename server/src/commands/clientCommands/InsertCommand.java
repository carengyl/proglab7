package commands.clientCommands;

import UDPutil.Request;
import UDPutil.Response;
import commands.AbstractCommand;
import commands.ArgumentValidationFunctions;
import commands.CommandArgument;
import commands.CommandData;
import commonUtil.HumanBeingFactory;
import commonUtil.Validators;
import exceptions.InvalidNumberOfArgsException;
import exceptions.NoUserInputException;
import exceptions.ValidationException;
import serverUtil.CommandProcessor;

import java.io.Serializable;
import java.util.Optional;

public class InsertCommand extends AbstractCommand implements Serializable {
    private final CommandProcessor commandProcessor;

    public InsertCommand(CommandProcessor commandProcessor) {
        super("insert",
                "Add element to collection by @key",
                1,
                "@key - unique long of element",
                ArgumentValidationFunctions.VALIDATE_KEY.getValidationFunction());
        this.commandProcessor = commandProcessor;
    }

    @Override
    public Optional<Response> executeCommand(Request request) throws NoUserInputException {
        long key = request.getCommandArgument().getLongArg();
        if (!commandProcessor.getCollectionManager().getHumanBeings().containsKey(key)) {
            if (request.getCommandArgument().getHumanBeingArgument() != null) {
                return Optional.of(commandProcessor.add(request));
            } else if (request.getCommandArgument().getElementArgument() != null) {
                HumanBeingFactory humanBeingFactory = new HumanBeingFactory();
                try {
                    humanBeingFactory.setVariables(request.getCommandArgument().getElementArgument());
                    request.getCommandArgument().setHumanBeingArgument(humanBeingFactory.getCreatedHumanBeing());
                    return Optional.of(commandProcessor.add(request));
                } catch (ValidationException e) {
                    return Optional.of(new Response(e.getMessage()));
                }
            } else {
                return Optional.of(new Response(this.getCommandData(), request.getCommandArgument()));
            }
        } else {
            return Optional.of(new Response("Key isn't unique"));
        }
    }

    @Override
    public CommandArgument validateArguments(CommandArgument arguments, CommandData commandData) throws ValidationException, InvalidNumberOfArgsException {
        Validators.validateNumberOfArgs(arguments.getNumberOfArgs(), commandData.numberOfArgs());
        long key = Validators.validateArg(arg -> true,
                "",
                Long::parseLong,
                arguments.getArg());
        arguments.setLongArg(key);
        return arguments;
    }
}
