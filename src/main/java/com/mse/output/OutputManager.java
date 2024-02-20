package com.mse.output;

import com.mse.enums.ConnectorType;
import lombok.Getter;

import java.util.Objects;

/**
 *
 */
@Getter
public class OutputManager {
    /**
     * The currently active output connector instance.
     */
    private static OutputConnector connector;

    /**
     * The type of the currently active output connector.
     */
    private static ConnectorType connectorType;

    /**
     * Retrieves an instance (new or existing) of the output connector based on the specified connector type.
     * @param type The type of the output connector to retrieve.
     * @return An instance of the output connector corresponding to the specified type.
     */
    public static OutputConnector getConnectorInstance (ConnectorType type) {
        if (connectorType == type && Objects.nonNull(connector)) {
            return connector;
        }

        // Makes sure the previous connector instance has been dropped and all open connections (File, Stream, DB) have been closed
        connector.drop();
        connectorType = type;

        // Additional connectors would go here once implemented. For example: Databases
        switch (type) {
            case JSON:
                connector = new JsonConnector();
                break;
        }

        return connector;
    }
}
