package com.example.analytics.controller.googleTrend;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.boot.web.servlet.error.ErrorController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorControllerImpl implements ErrorController, com.example.analytics.controller.googleTrend.ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        // Get the status code
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object error = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            // Handle different HTTP status codes appropriately
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("errorType", "404 Page Not Found");
                model.addAttribute("errorMessage", "The requested page was not found");
                return "error";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("errorType", "500 Internal Server Error");
                model.addAttribute("errorMessage", "An unexpected server error occurred");
                if (error != null) {
                    model.addAttribute("exception", error);
                }
                return "error";
            }
        }

        // Default error view
        model.addAttribute("errorType", "Unknown Error");
        model.addAttribute("errorMessage", "An unknown error occurred");
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}