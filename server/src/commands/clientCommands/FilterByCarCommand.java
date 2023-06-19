package commands.clientCommands;

import UDPutil.Response;
import commands.AbstractCommand;
import commands.ArgumentValidationFunctions;
import commands.CommandArgument;
import commands.CommandData;
import commonUtil.Validators;
import entities.CollectionManager;
import exceptions.InvalidNumberOfArgsException;
import exceptions.NoUserInputException;
import exceptions.ValidationException;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

public class FilterByCarCommand extends AbstractCommand implements Serializable {
    private final CollectionManager collection;

    public FilterByCarCommand(CollectionManager collection) {
        super("filter_by_car",
                "Show collection elements, which car equals @car",
                1,
                "@car - \"true\" string equals true, others to false",
                ArgumentValidationFunctions.VALIDATE_BOOLEAN.getValidationFunction());
        this.collection = collection;
    }

    @Override
    public Optional<Response> executeCommand(CommandArgument argument) throws NoUserInputException {
        boolean car = argument.isBooleanArg();

        Optional<Response> optionalResponse;

        if (!collection.getHumanBeings().isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (long key : collection.getHumanBeings().keySet()) {
                if (collection.getHumanBeings().get(key).getCar() != null) {
                    if (Objects.equals(collection.getHumanBeings().get(key).getCar().isCool(), car)) {
                        stringBuilder.append(collection.getHumanBeings().get(key)).append("\n");
                    }
                }
            }
            optionalResponse = Optional.of(new Response(stringBuilder.toString()));
        } else {
            optionalResponse = Optional.of(new Response("Collection is empty"));
        }
        return optionalResponse;
    }

    @Override
    public CommandArgument validateArguments(CommandArgument arguments, CommandData commandData) throws ValidationException, InvalidNumberOfArgsException {
        Validators.validateNumberOfArgs(arguments.getNumberOfArgs(), commandData.numberOfArgs());
        Boolean car = Validators.validateArg(arg -> true,
                "should be true or false",
                Boolean::parseBoolean,
                arguments.getArg());

        arguments.setBooleanArg(car);
        return arguments;
    }
}
