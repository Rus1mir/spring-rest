package web.сontroller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import web.model.Message;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({IllegalArgumentException.class})
    public ModelAndView error(IllegalArgumentException e) {
        ModelAndView modelAndView = new ModelAndView("error_page");
        Message message = new Message("The entity was not found", e.getMessage());
        modelAndView.addObject("message", message);
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView error(Exception e) {
        ModelAndView modelAndView = new ModelAndView("error_page");
        Message message = new Message("Server error detected", e.getMessage());
        modelAndView.addObject("message", message);
        return modelAndView;
    }
}
