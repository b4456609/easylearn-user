package soselab.easylearn.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by bernie on 2016/9/10.
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "User conflict")
public class UserConflictException extends RuntimeException {
}
