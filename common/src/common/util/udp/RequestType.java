package common.util.udp;

import java.io.Serializable;

public enum RequestType implements Serializable {
    COMMAND,
    REGISTER,
    LOGIN,
    INIT_COMMANDS
}
