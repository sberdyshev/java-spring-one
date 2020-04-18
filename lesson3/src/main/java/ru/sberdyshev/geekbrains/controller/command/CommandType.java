package ru.sberdyshev.geekbrains.controller.command;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents possible command variations
 *
 * @author sberdyshev
 */
public enum CommandType {
    GET_ALL_CUSTOMERS("/gtCsts", "Get all customers", "Gets all customers. Format - \"/gtCsts\".", 0),
    GET_CUSTOMER_BY_ID("/gtCst", "Get a customer by id", "Gets a customer by it's id. Format - \"/gtCst <customer id (Long)>\".", 1),
    GET_ALL_PRODUCTS("/gtPrds", "Get all products", "Gets all products. Format - \"/gtPrds\".", 0),
    GET_PRODUCT_BY_ID("/gtPrd", "Get a product by id", "Gets a product by it's id. Format - \"/gtPrd <product id (Long)>\".", 1),
    GET_PRODUCTS_BY_CUSTOMER_ID("/gtPrdsByCst", "Get products by a customer id", "Gets all products, that customer has bought. Format - \"/gtPrdsByCst <customer id (Long)>\".", 1),
    GET_CUSTOMERS_BY_PRODUCT_ID("/gtCstsByPrd", "Get customers by a product id", "Gets all customers who bought this product. Format - \"/gtCstsByPrd <product id (Long)>\".", 1),
    DELETE_CUSTOMER_BY_ID("/dltCst", "Delete a customer by id", "Deletes a customer by it's id. Format - \"/dltCst <customer id (Long)>\".", 1),
    DELETE_PRODUCT_BY_ID("/dltPrd", "Delete a product by id", "Deletes a product by it's id. Format - \"/dltPrd <customer id (Long)>\".", 1),

    HELP("/help", "help", "Get command list. Format - \"/help\".", 0),
    EXIT("/exit", "exit", "Exit. Format - \"/exit\".", 0),
    NONE(null, null, null, 0);

    private static final Logger logger = LoggerFactory.getLogger(CommandType.class);
    @Getter
    private String command;
    @Getter
    private String commandShortDescr;
    @Getter
    private String commandDescr;
    @Getter
    private int argsAmount;

    CommandType(String command, String commandShortDescr, String commandDescr, int argsAmount) {
        this.command = command;
        this.argsAmount = argsAmount;
        this.commandDescr = commandDescr;
        this.commandShortDescr = commandShortDescr;
    }
}
