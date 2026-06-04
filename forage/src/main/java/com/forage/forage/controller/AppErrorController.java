package com.forage.forage.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object statusCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object requestUri = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);

        model.addAttribute("statusCode", statusCode != null ? statusCode : "Erreur");
        model.addAttribute("requestUri", requestUri != null ? requestUri : "");
        model.addAttribute("message", message != null && !message.toString().isBlank()
                ? message
                : "Une erreur est survenue pendant le traitement de la demande.");

        return "error";
    }
}
