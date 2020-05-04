package ru.sberdyshev.geekbrains.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sberdyshev.geekbrains.controller.command.Command;
import ru.sberdyshev.geekbrains.controller.command.CommandType;
import ru.sberdyshev.geekbrains.entity.Customer;
import ru.sberdyshev.geekbrains.entity.Product;
import ru.sberdyshev.geekbrains.repository.CustomerProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class CustomerProductCLIController implements CLIController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerProductCLIController.class);
    private static final String PROCESSING_METHOD_START_DEBUG = "Processing \"{}\"";
    private static final String PROCESSING_METHOD_END_DEBUG = "Processed \"{}\"";
    private static final String PROCESSING_METHOD_OUTPUT_SEPARATOR = "-----------------------";
    private final int maxTryCount;
    private CustomerProductRepository customerProductRepository;
//    private static final String PROCESSIN_METHOD_NO_STACK_WARNING = "There is no stack with name \"{}\".";
//
//    private final Map<String, Stack<String>> stacks;
//    private final Map<String, Queue<String>> queues;
//    private static final String PROCESSIN_METHOD_NO_QUEUE_WARNING = "There is no queue with name \"{}\".";

    @Autowired
    public CustomerProductCLIController(CustomerProductRepository customerProductRepository) {
        this.maxTryCount = 3;
        this.customerProductRepository = customerProductRepository;
    }

    public void start() {
        logger.debug("CustomerProductCLIController has started");
        Scanner scanner = new Scanner(System.in);
        boolean isExit = false;
        int tryCount = 0;
        do {
            showEnterCommandInvitation();
            String line = scanner.nextLine();
            logger.debug("Line read \"{}\"", line);
            Command command = parse(line);
            if (!command.argsAreCorrrect()) {
                showWrongArgsMessage();
                tryCount += 1;
            } else {
                logger.debug("Chose \"{}\"", command.getType().getCommandShortDescr());
                switch (command.getType()) {
                    case EXIT:
                        showGoodBuyMessage();
                        isExit = true;
                        break;
                    case HELP: {
                        tryCount = 0;
                        showHelpMessage();
                        break;
                    }
                    case NONE: {
                        tryCount += 1;
                        logger.debug("Wrong command, tryCount = \"{}\"", tryCount);
                        isExit = processWrongCommand(tryCount);
                        break;
                    }
                    case GET_ALL_CUSTOMERS: {
                        tryCount = 0;
                        processGetAllCustomers(command);
                        break;
                    }
                    case GET_CUSTOMER_BY_ID: {
                        tryCount = 0;
                        processGetCustomerById(command);
                        break;
                    }
                    case GET_ALL_PRODUCTS: {
                        tryCount = 0;
                        processGetAllProducts(command);
                        break;
                    }
                    case GET_PRODUCT_BY_ID: {
                        tryCount = 0;
                        processGetProductById(command);
                        break;
                    }
                    case GET_PRODUCTS_BY_CUSTOMER_ID: {
                        tryCount = 0;
                        processGetProductsByCustomerId(command);
                        break;
                    }
                    case GET_CUSTOMERS_BY_PRODUCT_ID: {
                        tryCount = 0;
                        processGetCustomersByProductId(command);
                        break;
                    }
                    case DELETE_CUSTOMER_BY_ID: {
                        tryCount = 0;
                        processDeleteCustomerById(command);
                        break;
                    }
                    case DELETE_PRODUCT_BY_ID: {
                        tryCount = 0;
                        break;
                    }
                    default: {
                        IllegalStateException e = new IllegalStateException("Wrong command type: " + command.getType());
                        logger.error(e.getLocalizedMessage(), e);
                        throw e;
                    }
                }
            }
        } while (!isExit);
    }

    private void processGetAllCustomers(Command command) {
        System.out.println(PROCESSING_METHOD_OUTPUT_SEPARATOR);
        logger.debug("processGetAllCustomers() - " + PROCESSING_METHOD_START_DEBUG, command.getType().getCommandShortDescr());
        List<Customer> customers = customerProductRepository.getAllCustomers();
        if (customers == null) {
            System.out.println("There are no customers in the system");
            logger.debug("processGetAllCustomers() - found no customers.");
        } else {
            for (Customer customer : customers) {
                System.out.println("Customer id: " + customer.getId() + ", name: " + customer.getName());
                logger.debug("processGetAllCustomers() - found customer with id \"{}\".", customer.getId());
            }
        }
        logger.debug("processGetAllCustomers() - " + PROCESSING_METHOD_END_DEBUG, command.getType().getCommandShortDescr());
        System.out.println(PROCESSING_METHOD_OUTPUT_SEPARATOR);
    }

    private void processGetAllProducts(Command command) {
        System.out.println(PROCESSING_METHOD_OUTPUT_SEPARATOR);
        logger.debug("processGetAllProducts() - " + PROCESSING_METHOD_START_DEBUG, command.getType().getCommandShortDescr());
        List<Product> products = customerProductRepository.getAllProducts();
        if (products == null) {
            System.out.println("There are no products in the system");
            logger.debug("processGetAllProducts() - found no products.");
        } else {
            for (Product product : products) {
                System.out.println("Product id: " + product.getId() + ", name: " + product.getName() + ", cost: " + product.getCost());
                logger.debug("processGetAllProducts() - found product with id \"{}\".", product.getId());
            }
        }
        logger.debug("processGetAllProducts() - " + PROCESSING_METHOD_END_DEBUG, command.getType().getCommandShortDescr());
        System.out.println(PROCESSING_METHOD_OUTPUT_SEPARATOR);
    }

    private void processGetCustomerById(Command command) {
        System.out.println(PROCESSING_METHOD_OUTPUT_SEPARATOR);
        logger.debug("processGetCustomerById() - " + PROCESSING_METHOD_START_DEBUG, command.getType().getCommandShortDescr());
        String argument = command.getArgAtPos(0);
        logger.debug("processGetCustomerById() - Argument (customerId): \"{}\"", argument);
        Long customerId;
        try {
            customerId = new Long(argument);
        } catch (NumberFormatException ex) {
            System.out.println("Argument type is incorrect, try again. Expected Long. You entered: " + argument);
            logger.debug("processGetCustomerById() - Argument type is incorrect. Expected Long. Entered: \"{}\"", argument);
            return;
        }
        Customer customer = customerProductRepository.getCustomerById(customerId);
        if (customer == null) {
            System.out.println("No customer with id " + customerId + " found.");
            logger.debug("processGetCustomerById() - No customer with id \"{}\" found.", customerId);
        } else {
            System.out.println("Customer id: " + customer.getId() + ", name: " + customer.getName());
            logger.debug("processGetCustomerById() - Found customer id \"{}\", name \"{}\"", customer.getId(), customer.getName());
        }
        logger.debug("processGetCustomerById() - " + PROCESSING_METHOD_END_DEBUG, command.getType().getCommandShortDescr());
        System.out.println(PROCESSING_METHOD_OUTPUT_SEPARATOR);
    }

    private void processGetProductById(Command command) {
        System.out.println(PROCESSING_METHOD_OUTPUT_SEPARATOR);
        logger.debug("processGetProductById() - " + PROCESSING_METHOD_START_DEBUG, command.getType().getCommandShortDescr());
        String argument = command.getArgAtPos(0);
        logger.debug("processGetProductById() - Argument (productId): \"{}\"", argument);
        Long productId;
        try {
            productId = new Long(argument);
        } catch (NumberFormatException ex) {
            System.out.println("Argument type is incorrect, try again. Expected Long. You entered: " + argument);
            logger.debug("processGetProductById() - Argument type is incorrect. Expected Long. Entered: \"{}\"", argument);
            return;
        }
        Product product = customerProductRepository.getProductById(productId);
        if (product == null) {
            System.out.println("No product with id " + productId + " found.");
            logger.debug("processGetProductById() - No product with id \"{}\" found.", productId);
        } else {
            System.out.println("Product id: " + product.getId() + ", name: " + product.getName());
            logger.debug("processGetProductById() - Found product id \"{}\", name \"{}\"", product.getId(), product.getName());
        }
        logger.debug("processGetProductById() - " + PROCESSING_METHOD_END_DEBUG, command.getType().getCommandShortDescr());
        System.out.println(PROCESSING_METHOD_OUTPUT_SEPARATOR);
    }

    private void processGetProductsByCustomerId(Command command) {
        System.out.println(PROCESSING_METHOD_OUTPUT_SEPARATOR);
        logger.debug("processGetProductsByCustomerId() - " + PROCESSING_METHOD_START_DEBUG, command.getType().getCommandShortDescr());
        String argument = command.getArgAtPos(0);
        logger.debug("processGetProductsByCustomerId() - Argument (customerId): \"{}\"", argument);
        Long customerId;
        try {
            customerId = new Long(argument);
        } catch (NumberFormatException ex) {
            System.out.println("Argument type is incorrect, try again. Expected Long. You entered: " + argument);
            logger.debug("processGetProductsByCustomerId() - Argument type is incorrect. Expected Long. Entered: \"{}\"", argument);
            return;
        }
        List<Product> products = customerProductRepository.getProductsByCustomerId(customerId);
        if (products == null || products.isEmpty()) {
            System.out.println("No products where bought by a customer with id " + customerId + ".");
            logger.debug("processGetProductsByCustomerId() - No products where found related to a customer with id \"{}\".", customerId);
        } else {
            System.out.println("Customer with id " + customerId + " has bought:");
            for (Product product : products) {
                System.out.println("Product id: " + product.getId() + ", name: " + product.getName() + ", cost: " + product.getCost());
                logger.debug("processGetProductsByCustomerId() - Customer with id \"{}\" relates to a product with id \"{}\".", customerId, product.getId());
            }
        }
        logger.debug("processGetProductsByCustomerId() - " + PROCESSING_METHOD_END_DEBUG, command.getType().getCommandShortDescr());
        System.out.println(PROCESSING_METHOD_OUTPUT_SEPARATOR);
    }

    private void processGetCustomersByProductId(Command command) {
        System.out.println(PROCESSING_METHOD_OUTPUT_SEPARATOR);
        logger.debug("processGetCustomersByProductId() - " + PROCESSING_METHOD_START_DEBUG, command.getType().getCommandShortDescr());
        String argument = command.getArgAtPos(0);
        logger.debug("processGetCustomersByProductId() - Argument (productId): {}", argument);
        Long productId;
        try {
            productId = new Long(argument);
        } catch (NumberFormatException ex) {
            System.out.println("Argument type is incorrect, try again. Expected Long. You entered: " + argument);
            logger.debug("processGetCustomersByProductId() - Argument type is incorrect. Expected Long. Entered: \"{}\"", argument);
            return;
        }
        List<Customer> customers = customerProductRepository.getCustomersByProductId(productId);
        if (customers == null || customers.isEmpty()) {
            System.out.println("No customers have bought a product with id " + productId + ".");
            logger.debug("processGetCustomersByProductId() - No customers where found related to a product with id \"{}\".", productId);
        } else {
            System.out.println("Product with id " + productId + " has been bought by:");
            for (Customer customer : customers) {
                System.out.println("Customer id: " + customer.getId() + ", name: " + customer.getName() + ".");
                logger.debug("processGetCustomersByProductId() - Product with id \"{}\" relates to a customer with id \"{}\".", productId, customer.getId());
            }
        }
        logger.debug("processGetCustomersByProductId() - " + PROCESSING_METHOD_END_DEBUG, command.getType().getCommandShortDescr());
        System.out.println(PROCESSING_METHOD_OUTPUT_SEPARATOR);
    }

    private void processDeleteCustomerById(Command command) {
        System.out.println(PROCESSING_METHOD_OUTPUT_SEPARATOR);
        logger.debug("processDeleteCustomerById() - " + PROCESSING_METHOD_START_DEBUG, command.getType().getCommandShortDescr());
        String argument = command.getArgAtPos(0);
        logger.debug("processDeleteCustomerById() - Argument (customerId): {}", argument);
        Long customerId;
        try {
            customerId = new Long(argument);
        } catch (NumberFormatException ex) {
            System.out.println("Argument type is incorrect, try again. Expected Long. You entered: " + argument);
            logger.debug("processDeleteCustomerById() - Argument type is incorrect. Expected Long. Entered: \"{}\"", argument);
            return;
        }
        boolean isDeleted = customerProductRepository.deleteCustomerByCustomerId(customerId);
        if (isDeleted) {
            System.out.println("Customer with an id " + customerId + " has been successfully deleted.");
            logger.debug("processDeleteCustomerById() - Customer with an id \"{}\" has been successfully deleted.", customerId);
        } else {
            System.out.println("There is no customer with an id " + customerId + ".");
            logger.debug("processDeleteCustomerById() - There is no customer with an id \"{}\".", customerId);
        }
        logger.debug("processDeleteCustomerById() - " + PROCESSING_METHOD_END_DEBUG, command.getType().getCommandShortDescr());
        System.out.println(PROCESSING_METHOD_OUTPUT_SEPARATOR);
    }

    @Override
    public Command parse(String line) {
        logger.debug("parse() - Parsing line: \"{}\"", line);
        //проверка совпадения команды в командной строке и опредеения команды для каждого определения
        for (CommandType commandType : CommandType.values()) {
            if (commandType.equals(CommandType.NONE)) {
                continue;
            }
            logger.debug("parse() - Checking command {}", commandType.getCommand());
            //Начало строки совпадает?
            boolean lineStartsWithCurrentCommand = line.startsWith(commandType.getCommand());
            //У команды есть аргументы (длина больше, чем длина команды в определении)?
            boolean commandHasArgs = line.length() > commandType.getCommand().length();
            //Аргументы оттделены пробелом?
            boolean commandSeparatedFromArgsWithSpace = line.startsWith(commandType.getCommand() + " ");
            logger.debug("parse() - The beginning of the input is the same - {}; " +
                            "command has arguments - {}; " +
                            "arguments are separated from the command - {}",
                    lineStartsWithCurrentCommand,
                    commandHasArgs,
                    commandSeparatedFromArgsWithSpace);
            if (lineStartsWithCurrentCommand) {
                if (!commandHasArgs || (commandHasArgs && commandSeparatedFromArgsWithSpace)) {
                    List<String> args = getArgs(line, commandType);
                    logger.debug("parse() - Command type: \"{}\"", commandType);
                    logger.debug("parse() - Arguments: \"{}\"", args);
                    return new Command(commandType, args);
                }
            }
        }
        return new Command(CommandType.NONE, new ArrayList<>());
    }

    private List<String> getArgs(String line, CommandType commandType) {
        List<String> args = new ArrayList<>();
        int commandLength = commandType.getCommand().length();
        String lineWithoutCommand;
        if (commandLength < line.length()) {
            lineWithoutCommand = line.substring(commandLength);
        } else {
            return args;
        }
        int leadingIndexOfWhiteSpaceForAPreviousCommand = 0;
        int leadingIndexOfWhiteSpace = 0;
        int trailingIndexOfWhiteSpace = 0;
        int spacesAmountInTheLine = (int) line.chars().filter(ch -> ch == ' ').count();
        for (int i = 0; i < spacesAmountInTheLine; i++) {
            leadingIndexOfWhiteSpace = lineWithoutCommand.indexOf(' ', leadingIndexOfWhiteSpaceForAPreviousCommand);
            trailingIndexOfWhiteSpace = lineWithoutCommand.indexOf(' ', leadingIndexOfWhiteSpace + 1);
            String arg;
            if (leadingIndexOfWhiteSpace >= trailingIndexOfWhiteSpace) {
                arg = lineWithoutCommand.substring(leadingIndexOfWhiteSpace).trim();
            } else {
                arg = lineWithoutCommand.substring(leadingIndexOfWhiteSpace, trailingIndexOfWhiteSpace).trim();
            }
            if ("".equals(arg)) {
                arg = null;
            }
            args.add(arg);
            if (trailingIndexOfWhiteSpace < 0) {
                break;
            }
            leadingIndexOfWhiteSpaceForAPreviousCommand = trailingIndexOfWhiteSpace;
        }
        return args;
    }

    private boolean processWrongCommand(int tryCount) {
        boolean isExit = false;
        if (tryCount >= maxTryCount) {
            showExaustedTriesMessage();
            showGoodBuyMessage();
            isExit = true;
        } else {
            showWrongCommandMessage();
        }
        return isExit;
    }
}
