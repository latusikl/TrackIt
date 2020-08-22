package pl.latusikl.trackit.trackerservice.server.coban.validators;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Predicate;

@Slf4j
public class MessageValidators
{

    public static Predicate<String> loginMessageValidator()
    {
        return new LoginMessageValidator();
    }

}
