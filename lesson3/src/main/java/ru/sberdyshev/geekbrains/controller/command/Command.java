package ru.sberdyshev.geekbrains.controller.command;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

/**
 * Command representation
 *
 * @author sberdyshev
 */
public class Command {
    private static final Logger logger = LoggerFactory.getLogger(Command.class);
    @Getter
    private CommandType type;
    private List<String> arguments;

    public Command(CommandType type, List<String> arguments) {
        this.type = type;
        this.arguments = arguments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Command)) return false;
        Command command = (Command) o;
        return getType() == command.getType() &&
                Objects.equals(arguments, command.arguments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(), arguments);
    }

    public String getArgAtPos(int position) {
        String argument;
        if (arguments != null) {
            argument = arguments.get(position);
            logger.debug("getArgAtPos() - got argument \"{}\" at position \"{}\".", argument, position);
        } else {
            logger.debug("getArgAtPos() - arguments are null.");
        }
        return arguments != null ? arguments.get(position) : null;
    }

    private boolean checkEnoughArgs(int argsAmount) {
        boolean result = false;
        if ((arguments != null && type != null) && (arguments.size() == argsAmount)) {
            result = true;
        }
        return result;
    }

    public boolean argsAreCorrrect() {
        int argsAmount = arguments.size();
        if (!checkEnoughArgs(argsAmount)) {
            logger.debug("Command type {}. Amount of params {}. Expected amount {}. Amount of arguments is incorrect.", type, argsAmount, type.getArgsAmount());
            return false;
        }
        for (int i = 0; i < arguments.size(); i++) {
            if (arguments.get(i) == null) {
                logger.debug("Command type {}. Argument #{} is null. Arguments are incorrect.", type, i + 1);
                return false;
            }
        }
        if (argsAmount == type.getArgsAmount()) {
            logger.debug("Command type {}. Amount of params {}. Expected amount {}. Amount of arguments is correct.", type, argsAmount, type.getArgsAmount());
            return true;
        } else {
            logger.debug("Command type {}. Amount of params {}. Expected amount {}. Arguments are not correct.", type, argsAmount, type.getArgsAmount());
            return false;
        }
    }
}
